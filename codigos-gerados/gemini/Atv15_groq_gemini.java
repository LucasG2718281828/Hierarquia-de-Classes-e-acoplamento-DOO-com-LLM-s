**Java – Hierarquia de relatórios multi‑tenant**

```java
package com.example.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe base que consolida métricas multi‑tenant, armazena em cache Redis
 * e exporta relatórios em PDF ou CSV.  
 * Todas as dependências são injetadas via construtor.
 */
public abstract class RelatorioAnalitico {

    /* ---------- Dependências ---------- */
    protected final DataWarehouseClient dwClient;
    protected final CacheRedis cacheRedis;
    protected final ExportadorPdf exportadorPdf;
    protected final ExportadorCsv exportadorCsv;
    protected final PermissionChecker permissionChecker;
    protected final MotorTemplate motorTemplate;
    protected final EnvioEmailService envioEmailService;
    protected final AgendadorJob agendadorJob;
    protected final LogAudit logAudit;

    /* ---------- Construtor ---------- */
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

    /* ---------- Métodos de consolidação ---------- */
    /**
     * Consolida métricas de todos os tenants informados.
     * Utiliza cache Redis para evitar chamadas repetidas ao Data Warehouse.
     *
     * @param tenantIds Lista de identificadores de tenants
     * @return Map contendo as métricas consolidadas
     */
    public Map<String, Object> consolidateMetrics(List<String> tenantIds) {
        // Verifica permissão
        if (!permissionChecker.hasPermission("CONSOLIDATE_METRICS")) {
            throw new SecurityException("Permission denied");
        }

        // Chave de cache
        String cacheKey = "metrics:" + String.join(",", tenantIds);

        // Tenta obter do cache
        Map<String, Object> metrics = cacheRedis.get(cacheKey);
        if (metrics == null) {
            // Busca no Data Warehouse
            metrics = dwClient.fetchMetrics(tenantIds);
            // Salva no cache
            cacheRedis.set(cacheKey, metrics);
        }

        logAudit.log("Consolidated metrics for tenants: " + tenantIds);
        return metrics;
    }

    /* ---------- Métodos de exportação ---------- */
    /**
     * Exporta os dados do relatório no formato solicitado.
     *
     * @param format  "PDF" ou "CSV" (case‑insensitive)
     * @param data    Dados a serem exportados
     * @return Byte array contendo o arquivo exportado
     */
    public byte[] exportReport(String format, Map<String, Object> data) {
        if ("PDF".equalsIgnoreCase(format)) {
            return exportadorPdf.export(data);
        } else if ("CSV".equalsIgnoreCase(format)) {
            return exportadorCsv.export(data);
        } else {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }

    /*