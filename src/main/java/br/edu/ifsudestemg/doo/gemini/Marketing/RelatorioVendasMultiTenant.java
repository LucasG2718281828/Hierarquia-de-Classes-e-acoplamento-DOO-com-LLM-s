package br.edu.ifsudestemg.doo.gemini.Marketing;

public class RelatorioVendasMultiTenant extends RelatorioAnalitico {

    private String tenantId;

    public RelatorioVendasMultiTenant(String tenantId,
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
        this.tenantId = tenantId;
    }

    @Override
    public String obterNomeRelatorio() {
        return "Relatório de Vendas - Tenant " + tenantId;
    }

    @Override
    public Object gerarConteudo() {
        // Lógica fictícia para gerar conteúdo específico de vendas
        Object rawData = dataWarehouseClient.obterVendasPorTenant(tenantId);
        return motorTemplate.processar(rawData);
    }

    @Override
    public void consolidarMetricasMultitenant() {
        // Pode reutilizar a implementação da classe pai ou acrescentar lógica específica
        super.consolidarMetricasMultitenant();
        // Exemplo de lógica adicional:
        logAudit.registrar("Consolidação de métricas de vendas para tenant " + tenantId);
    }
}