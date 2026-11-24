package br.edu.ifsudestemg.doo.groq.Marketing;

import java.util.Map;

public abstract class RelatorioAnalitico {

    protected DataWarehouseClient dwClient;
    protected CacheRedis cacheRedis;
    protected ExportadorPdf exportadorPdf;
    protected ExportadorCsv exportadorCsv;
    protected PermissionChecker permissionChecker;
    protected MotorTemplate motorTemplate;
    protected EnvioEmailService envioEmailService;
    protected AgendadorJob agendadorJob;
    protected LogAudit logAudit;

    public RelatorioAnalitico(DataWarehouseClient dwClient,
                              CacheRedis cacheRedis,
                              ExportadorPdf exportadorPdf,
                              ExportadorCsv exportadorCsv,
                              PermissionChecker permissionChecker,
                              MotorTemplate motorTemplate,
                              EnvioEmailService envioEmailService,
                              AgendadorJob agendadorJob,
                              LogAudit logAudit) {
        this.dwClient = dwClient;
        this.cacheRedis = cacheRedis;
        this.exportadorPdf = exportadorPdf;
        this.exportadorCsv = exportadorCsv;
        this.permissionChecker = permissionChecker;
        this.motorTemplate = motorTemplate;
        this.envioEmailService = envioEmailService;
        this.agendadorJob = agendadorJob;
        this.logAudit = logAudit;
    }

    public Map<String, Object> consolidateMetrics(String tenantId) {
        Map<String, Object> metrics = cacheRedis.getMetrics(tenantId);
        if (metrics == null) {
            metrics = dwClient.fetchMetrics(tenantId);
            cacheRedis.setMetrics(tenantId, metrics);
        }
        return metrics;
    }

    public byte[] exportReport(String tenantId, String format) {
        Map<String, Object> metrics = consolidateMetrics(tenantId);
        byte[] data;
        if ("pdf".equalsIgnoreCase(format)) {
            data = exportadorPdf.export(metrics);
        } else if ("csv".equalsIgnoreCase(format)) {
            data = exportadorCsv.export(metrics);
        } else {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
        return data;
    }

    public void sendReportEmail(String tenantId, String format, String recipient) {
        if (!permissionChecker.hasPermission(tenantId, "SEND_REPORT")) {
            throw new SecurityException("Permission denied for tenant: " + tenantId);
        }
        byte[] report = exportReport(tenantId, format);
        envioEmailService.sendEmail(recipient, "Relatório " + format.toUpperCase(), report);
        logAudit.log("Report sent to " + recipient + " for tenant " + tenantId);
    }

    public void scheduleReportJob(String tenantId, String format, String cronExpression) {
        agendadorJob.schedule(() -> sendReportEmail(tenantId, format, "report@example.com"),
                cronExpression);
        logAudit.log("Scheduled report job for tenant " + tenantId);
    }

    public void auditLog(String tenantId, String action) {
        logAudit.log("Tenant " + tenantId + ": " + action);
    }
}