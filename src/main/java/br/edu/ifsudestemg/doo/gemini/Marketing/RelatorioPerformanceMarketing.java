package br.edu.ifsudestemg.doo.gemini.Marketing;

public class RelatorioPerformanceMarketing extends RelatorioAnalitico {

    private String campanhaId;

    public RelatorioPerformanceMarketing(String campanhaId,
                                         DataWarehouseClient dataWarehouseClient,
                                         CacheRedis cacheRedis,
                                         ExportadorPdf exportadorPdf,
                                         ExportadorCsv exportadorCsv,
                                         PermissionChecker permissionChecker,
                                         MotorTemplate motorTemplate,
                                         EnvioEmailService envioEmailService,
                                         AgendadorJob agendadorJob,
                                         LogAudit logAudit) {
        super(dataWarehouseClient, cacheRedis, exportadorPdf, exportadorCsv,
              permissionChecker, motorTemplate, envioEmailService, agendadorJob, logAudit);
        this.campanhaId = campanhaId;
    }

    @Override
    public String obterNomeRelatorio() {
        return "Relatório de Performance de Marketing - Campanha " + campanhaId;
    }

    @Override
    public Object gerarConteudo() {
        // Lógica fictícia para gerar conteúdo específico de marketing
        Object rawData = dataWarehouseClient.obterPerformanceMarketing(campanhaId);
        return motorTemplate.processar(rawData);
    }

    @Override
    public void consolidarMetricasMultitenant() {
        // Implementação específica, ainda chamando a lógica comum
        super.consolidarMetricasMultitenant();
        logAudit.registrar("Consolidação de métricas de marketing para campanha " + campanhaId);
    }
}