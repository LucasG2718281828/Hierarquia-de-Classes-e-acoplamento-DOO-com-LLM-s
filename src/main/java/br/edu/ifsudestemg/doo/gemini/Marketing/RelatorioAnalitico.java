package br.edu.ifsudestemg.doo.gemini.Marketing;

public abstract class RelatorioAnalitico {

    protected DataWarehouseClient dataWarehouseClient;
    protected CacheRedis cacheRedis;
    protected ExportadorPdf exportadorPdf;
    protected ExportadorCsv exportadorCsv;
    protected PermissionChecker permissionChecker;
    protected MotorTemplate motorTemplate;
    protected EnvioEmailService envioEmailService;
    protected AgendadorJob agendadorJob;
    protected LogAudit logAudit;

    public RelatorioAnalitico(DataWarehouseClient dataWarehouseClient,
                              CacheRedis cacheRedis,
                              ExportadorPdf exportadorPdf,
                              ExportadorCsv exportadorCsv,
                              PermissionChecker permissionChecker,
                              MotorTemplate motorTemplate,
                              EnvioEmailService envioEmailService,
                              AgendadorJob agendadorJob,
                              LogAudit logAudit) {
        this.dataWarehouseClient = dataWarehouseClient;
        this.cacheRedis = cacheRedis;
        this.exportadorPdf = exportadorPdf;
        this.exportadorCsv = exportadorCsv;
        this.permissionChecker = permissionChecker;
        this.motorTemplate = motorTemplate;
        this.envioEmailService = envioEmailService;
        this.agendadorJob = agendadorJob;
        this.logAudit = logAudit;
    }

    /**
     * Consolida métricas de múltiplos tenants usando cache Redis.
     */
    public void consolidarMetricasMultitenant() {
        if (!permissionChecker.hasPermission("CONSOLIDAR_METRICAS")) {
            throw new SecurityException("Permissão negada para consolidar métricas.");
        }

        // Tenta obter dados do cache
        Object dadosCache = cacheRedis.get("metricas_consolidadas");
        if (dadosCache != null) {
            logAudit.registrar("Métricas consolidadas obtidas do cache.");
            return;
        }

        // Caso não exista no cache, busca no Data Warehouse
        Object dadosDW = dataWarehouseClient.obterMetricas();
        // Processa os dados (lógica fictícia)
        Object metricasProcessadas = motorTemplate.processar(dadosDW);

        // Armazena no cache para próximas consultas
        cacheRedis.set("metricas_consolidadas", metricasProcessadas);
        logAudit.registrar("Métricas consolidadas armazenadas no cache.");
    }

    /**
     * Exporta o relatório em diferentes formatos (PDF, CSV).
     *
     * @param formato "PDF" ou "CSV"
     */
    public void exportar(String formato) {
        if (!permissionChecker.hasPermission("EXPORTAR_RELATORIO")) {
            throw new SecurityException("Permissão negada para exportar relatório.");
        }

        switch (formato.toUpperCase()) {
            case "PDF":
                exportadorPdf.exportar(this);
                logAudit.registrar("Relatório exportado em PDF.");
                break;
            case "CSV":
                exportadorCsv.exportar(this);
                logAudit.registrar("Relatório exportado em CSV.");
                break;
            default:
                throw new IllegalArgumentException("Formato de exportação não suportado: " + formato);
        }
    }

    /**
     * Envia o relatório por e‑mail.
     *
     * @param destinatario endereço de e‑mail do destinatário
     */
    public void enviarPorEmail(String destinatario) {
        if (!permissionChecker.hasPermission("ENVIAR_EMAIL")) {
            throw new SecurityException("Permissão negada para enviar e‑mail.");
        }
        envioEmailService.enviar(this, destinatario);
        logAudit.registrar("Relatório enviado por e‑mail para " + destinatario);
    }

    /**
     * Agenda a geração automática do relatório.
     *
     * @param cronExpression expressão cron para agendamento
     */
    public void agendarGeracao(String cronExpression) {
        if (!permissionChecker.hasPermission("AGENDAR_JOB")) {
            throw new SecurityException("Permissão negada para agendar job.");
        }
        agendadorJob.agendar(this, cronExpression);
        logAudit.registrar("Job de geração de relatório agendado com cron: " + cronExpression);
    }

    // Métodos que podem ser sobrescritos pelas subclasses
    public abstract String obterNomeRelatorio();

    public abstract Object gerarConteudo();
}