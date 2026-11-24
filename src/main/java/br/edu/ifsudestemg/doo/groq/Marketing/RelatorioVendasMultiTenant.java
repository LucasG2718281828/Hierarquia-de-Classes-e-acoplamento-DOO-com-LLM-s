package br.edu.ifsudestemg.doo.groq.Marketing;

import java.util.Map;

public class RelatorioVendasMultiTenant extends RelatorioAnalitico {

    public RelatorioVendasMultiTenant(DataWarehouseClient dwClient,
                                      CacheRedis cacheRedis,
                                      ExportadorPdf exportadorPdf,
                                      ExportadorCsv exportadorCsv,
                                      PermissionChecker permissionChecker,
                                      MotorTemplate motorTemplate,
                                      EnvioEmailService envioEmailService,
                                      AgendadorJob agendadorJob,
                                      LogAudit log