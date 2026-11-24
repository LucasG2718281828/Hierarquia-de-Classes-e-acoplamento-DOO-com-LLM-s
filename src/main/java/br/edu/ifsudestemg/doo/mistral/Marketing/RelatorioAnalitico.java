package br.edu.ifsudestemg.doo.mistral.Marketing;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class RelatorioAnalitico {
    private String idRelatorio;
    private String nomeRelatorio;
    private String tenantId;
    private Map<String, Object> metricas;
    
    private DataWarehouseClient dataWarehouseClient;
    private CacheRedis cacheRedis;
    private ExportadorPdf exportadorPdf;
    private ExportadorCsv exportadorCsv;
    private PermissionChecker permissionChecker;
    private MotorTemplate motorTemplate;
    private EnvioEmailService envioEmailService;
    private AgendadorJob agendadorJob;
    private LogAudit logAudit;

    public RelatorioAnalitico() {
        this.metricas = new HashMap<>();
        // Inicialização das dependências (stubs)
        this.dataWarehouseClient = new DataWarehouseClient();
        this.cacheRedis = new CacheRedis();
        this.exportadorPdf = new ExportadorPdf();
        this.exportadorCsv = new ExportadorCsv();
        this.permissionChecker = new PermissionChecker();
        this.motorTemplate = new MotorTemplate();
        this.envioEmailService = new EnvioEmailService();
        this.agendadorJob = new AgendadorJob();
        this.logAudit = new LogAudit();
    }

    public void consolidarMetricasMultiTenant(List<String> tenantIds) {
        logAudit.registrarAcao("Consolidação de métricas iniciada para tenants: " + tenantIds);
        
        Map<String, Object> metricasConsolidadas = new HashMap<>();
        
        for (String tenantId : tenantIds) {
            // Verificar permissão
            if (!permissionChecker.verificarPermissao(tenantId, "CONSOLIDAR_METRICAS")) {
                logAudit.registrarAcao("Permissão negada para tenant: " + tenantId);
                continue;
            }
            
            // Verificar cache
            String cacheKey = "metricas:" + tenantId;
            Object cachedData = cacheRedis.get(cacheKey);
            
            if (cachedData != null) {
                // Usar dados do cache
                mergeMetricas(metricasConsolidadas, (Map<String, Object>) cachedData);
            } else {
                // Buscar no Data Warehouse
                Map<String, Object> dadosDw = dataWarehouseClient.buscarMetricas(tenantId);
                if (dadosDw != null) {
                    // Salvar no cache
                    cacheRedis.set(cacheKey, dadosDw, 3600); // 1 hora de TTL
                    mergeMetricas(metricasConsolidadas, dadosDw);
                }
            }
        }
        
        this.metricas = metricasConsolidadas;
        logAudit.registrarAcao("Consolidação de métricas finalizada");
    }

    private void mergeMetricas(Map<String, Object> destino, Map<String, Object> origem) {
        for (Map.Entry<String, Object> entry : origem.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            if (destino.containsKey(key)) {
                // Lógica de agregação (ex: soma para números)
                if (destino.get(key) instanceof Number && value instanceof Number) {
                    double soma = ((Number) destino.get(key)).doubleValue() + ((Number) value).doubleValue();
                    destino.put(key, soma);
                } else {
                    // Para outros tipos, pode-se manter o último ou concatenar
                    destino.put(key, value);
                }
            } else {
                destino.put(key, value);
            }
        }
    }

    public byte[] exportarMultiFormato(String formato) {
        logAudit.registrarAcao("Exportação solicitada no formato: " + formato);
        
        byte[] resultado = null;
        
        switch (formato.toLowerCase()) {
            case "pdf":
                resultado = exportadorPdf.gerarPdf(this.metricas, this.nomeRelatorio);
                break;
            case "csv":
                resultado = exportadorCsv.gerarCsv(this.metricas);
                break;
            default:
                throw new IllegalArgumentException("Formato de exportação não suportado: " + formato);
        }
        
        logAudit.registrarAcao("Exportação concluída com sucesso");
        return resultado;
    }

    public void agendarEnvioRelatorio(String emailDestino, String formato, String cronExpression) {
        logAudit.registrarAcao("Agendamento de envio para: " + emailDestino);
        
        // Gerar o relatório
        byte[] conteudo = exportarMultiFormato(formato);
        
        // Agendar o envio
        agendadorJob.agendarTarefa(cronExpression, () -> {
            envioEmailService.enviarEmailComAnexo(emailDestino, "Relatório: " + this.nomeRelatorio, conteudo, formato);
            logAudit.registrarAcao("Relatório enviado para: " + emailDestino);
        });
    }

    public void aplicarTemplate(String templateId) {
        logAudit.registrarAcao("Aplicando template: " + templateId);
        motorTemplate.aplicarTemplate(this.metricas, templateId);
    }

    // Getters e Setters
    public String getIdRelatorio() { return idRelatorio; }
    public void setIdRelatorio(String idRelatorio) { this.idRelatorio = idRelatorio; }
    
    public String getNomeRelatorio() { return nomeRelatorio; }
    public void setNomeRelatorio(String nomeRelatorio) { this.nomeRelatorio = nomeRelatorio; }
    
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    
    public Map<String, Object> getMetricas() { return metricas; }
    public void setMetricas(Map<String, Object> metricas) { this.metricas = metricas; }
}