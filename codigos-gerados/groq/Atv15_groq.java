```java
/*======================================================================
 *  Relatório Analítico – Hierarquia Principal
 *====================================================================*/

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe abstrata que encapsula a lógica comum de relatórios analíticos
 * multi‑tenant.  Ela oferece:
 *   • consolidação de métricas com cache Redis
 *   • exportação em PDF ou CSV
 *   • envio de e‑mail
 *   • agendamento de jobs
 *   • auditoria
 *
 * As dependências são injetadas via construtor para facilitar testes
 * e manter a classe desacoplada.
 */
public abstract class RelatorioAnalitico {

    /* ------------------------------------------------------------------
     *  Dependências
     * ------------------------------------------------------------------ */
    protected final DataWarehouseClient dwClient;
    protected final CacheRedis cacheRedis;
    protected final ExportadorPdf exportadorPdf;
    protected final ExportadorCsv exportadorCsv;
    protected final PermissionChecker permissionChecker;
    protected final MotorTemplate motorTemplate;
    protected final EnvioEmailService envioEmailService;
    protected final AgendadorJob agendadorJob;
    protected final LogAudit logAudit;

    /* ------------------------------------------------------------------
     *  Construtor
     * ------------------------------------------------------------------ */
    public RelatorioAnalitico(
            DataWarehouseClient dwClient,
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

    /* ------------------------------------------------------------------
     *  Consolidação de métricas multi‑tenant
     * ------------------------------------------------------------------ */
    /**
     * Busca métricas de todos os tenants informados.  Se o cache Redis
     * já contiver os dados, eles são retornados imediatamente.
     *
     * @param tenantIds Lista de identificadores de tenant
     * @return Map contendo as métricas consolidadas
     */
    public Map<String, Object> consolidateMetrics(List<String> tenantIds) {
        // Verifica permissão
        if (!permissionChecker.hasPermission("CONSOLIDATE_METRICS")) {
            throw new SecurityException("Permission denied");
        }

        // Chave de cache única
        String cacheKey = "metrics:" + String.join(",", tenantIds);

        // Tenta ler do cache
        Map<String, Object> metrics = cacheRedis.get(cacheKey);
        if (metrics == null) {
            // Busca no Data Warehouse
            metrics = dwClient.fetchMetrics(tenantIds);
            // Persiste no cache
            cacheRedis.set(cacheKey, metrics);
        }

        logAudit.log("Consolidated metrics for tenants: " + tenantIds);
        return metrics;
    }

    /* ------------------------------------------------------------------
     *  Exportação de relatórios
     * ------------------------------------------------------------------ */
    /**
     * Exporta os dados do relatório no formato solicitado.
     *
     * @param format  "PDF" ou "CSV" (case‑insensitive)
     * @param data    Dados a serem exportados
     * @return Byte array contendo o arquivo exportado
     */
    public byte[] exportReport(String format, Map<String, Object> data) {
        if ("PDF".equalsIgnoreCase(format)) {
            return export