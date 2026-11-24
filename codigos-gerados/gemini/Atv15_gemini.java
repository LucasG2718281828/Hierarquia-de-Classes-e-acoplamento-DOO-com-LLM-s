Aqui está a implementação completa da hierarquia de classes em Java, juntamente com os stubs (estruturas básicas) para as dependências externas necessárias para compilação.

---

### 1. Dependências Externas (Stubs)

```java
import java.util.Map;

// ==========================================
// STUBS DE DEPENDÊNCIA (Apenas esqueletos)
// ==========================================

class DataWarehouseClient {
    public Map<String, Object> executarConsultaMetricas(String tenantId, String query, Map<String, Object> params) {
        return Map.of();
    }
}

class CacheRedis {
    public Object get(String chave) { return null; }
    public void set(String chave, Object valor, long ttlSegundos) {}
    public boolean existe(String chave) { return false; }
}

class ExportadorPdf {
    public byte[] exportar(String conteudoRenderizado, Map<String, Object> metricas) {
        return new byte[0];
    }
}

class ExportadorCsv {
    public byte[] exportar(Map<String, Object> metricas) {
        return new byte[0];
    }
}

class PermissionChecker {
    public boolean temPermissao(String tenantId, String usuarioId, String permissaoNecessaria) {
        return true;
    }
}

class MotorTemplate {
    public String renderizar(String nomeTemplate, Map<String, Object> contexto) {
        return "";
    }
}

class EnvioEmailService {
    public void enviarComAnexo(String destinatario, String assunto, String corpo, byte[] anexo, String nomeArquivo) {}
}

class AgendadorJob {
    public void agendar(String cronExpressao, Runnable tarefa) {}
}

class LogAudit {
    public void registrar(String tenantId, String usuarioId, String operacao, String detalhes) {}
}
```

---

### 2. Classe Base: `RelatorioAnalitico`

```java
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe base abstrata responsável por padronizar o ciclo de vida da geração
 * de relatórios analíticos multi-tenant com cache e suporte a múltiplos formatos.
 */
public abstract class RelatorioAnalitico {

    protected final DataWarehouseClient dwClient;
    protected final CacheRedis cacheRedis;
    protected final ExportadorPdf exportadorPdf;
    protected final ExportadorCsv exportadorCsv;
    protected final PermissionChecker permissionChecker;
    protected final MotorTemplate motorTemplate;
    protected final EnvioEmailService envioEmailService;
    protected final AgendadorJob agendadorJob;
    protected final LogAudit logAudit;

    private static final long TTL_CACHE_PADRAO_SEGUNDOS = 3600; // 1 hora

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
        this.dwClient = Objects.requireNonNull(dwClient, "DataWarehouseClient não pode ser nulo");
        this.cacheRedis = Objects.requireNonNull(cacheRedis, "CacheRedis não pode ser nulo");
        this.exportadorPdf = Objects.requireNonNull(exportadorPdf, "ExportadorPdf não pode ser nulo");
        this.exportadorCsv = Objects.requireNonNull(exportadorCsv, "ExportadorCsv não pode ser nulo");
        this.permissionChecker = Objects.requireNonNull(permissionChecker, "PermissionChecker não pode ser nulo");
        this.motorTemplate = Objects.requireNonNull(motorTemplate, "MotorTemplate não pode ser nulo");
        this.envioEmailService = Objects.requireNonNull(envioEmailService, "EnvioEmailService não pode ser nulo");
        this.agendadorJob = Objects.requireNonNull(agendadorJob, "AgendadorJob não pode ser nulo");
        this.logAudit = Objects.requireNonNull(logAudit, "LogAudit não pode ser nulo");
    }

    /**
     * Executa a rotina consolidada com validação de permissão, resolução de cache,
     * renderização e auditoria.
     */
    public final byte[] gerarRelatorio(String tenantId, String usuarioId, String formato, Map<String, Object> parametros) {
        validarParametrosObrigatorios(tenantId, usuarioId, formato);

        // 1. Checagem de Segurança
        if (!permissionChecker.temPermissao(tenantId, usuarioId, "RELATORIO_VIEW_" + getTipoRelatorio())) {
            logAudit.registrar(tenantId, usuarioId, "ACESSO_NEGADO", "Tentativa não autorizada ao relatório " + getTipoRelatorio());
            throw new SecurityException("Usuário não possui permissão para gerar este relatório analítico.");
        }

        // 2. Resolução de Cache Redis
        String chaveCache = gerarChaveCache(tenantId, parametros);
        @SuppressWarnings("unchecked")
        Map<String, Object> metricas = (Map<String, Object>) cacheRedis.get(chaveCache);

        if (metricas == null || metricas.isEmpty()) {
            metricas = consolidarMetricas(tenantId, parametros);
            cacheRedis.set(chaveCache, metricas, getTempoExpiracaoCache());
            logAudit.registrar(tenantId, usuarioId, "CACHE_MISS", "Métricas recalculadas e armazenadas no cache: " + chaveCache);
        } else {
            logAudit.registrar(tenantId, usuarioId, "CACHE_HIT", "Métricas recuperadas via Redis: " + chaveCache);
        }

        // 3. Exportação Multi-formato
        byte[] relatorioExportado = exportarParaFormato(metricas, formato);

        // 4. Auditoria
        logAudit.registrar(tenantId, usuarioId, "RELATORIO_GERADO", 
            String.format("Relatório %s gerado no formato %s", getTipoRelatorio(), formato.toUpperCase()));

        return relatorioExportado;
    }

    /**
     * Agenda a geração e envio automático do relatório.
     */
    public void agendarEnvioEmail(String tenantId, String usuarioId, String cronExpressao, 
                                  String emailDestino, String formato, Map<String, Object> parametros) {
        agendadorJob.agendar(cronExpressao, () -> {
            try {
                byte[] relatorio = gerarRelatorio(tenantId, usuarioId, formato, parametros);
                String nomeArquivo = String.format("%s_%s.%s", getTipoRelatorio().toLowerCase(), tenantId, formato.toLowerCase());
                envioEmailService.enviarComAnexo(
                    emailDestino,
                    "Relatório Analítico Agendado: " + getTipoRelatorio(),
                    "Em anexo segue o relatório analítico consolidado gerado automaticamente.",
                    relatorio,
                    nomeArquivo
                );
                logAudit.registrar(tenantId, usuarioId, "AGENDAMENTO_EXECUTADO", "Relatório enviado para " + emailDestino);
            } catch (Exception e) {
                logAudit.registrar(tenantId, usuarioId, "AGENDAMENTO_FALHA", "Falha no disparo: " + e.getMessage());
            }
        });
    }

    protected byte[] exportarParaFormato(Map<String, Object> metricas, String formato) {
        switch (formato.toUpperCase()) {
            case "PDF":
                String htmlRenderizado = motorTemplate.renderizar(getNomeTemplate(), metricas);
                return exportadorPdf.exportar(htmlRenderizado, metricas);
            case "CSV":
                return exportadorCsv.exportar(metricas);
            default:
                throw new IllegalArgumentException("Formato de exportação não suportado: " + formato);
        }
    }

    protected String gerarChaveCache(String tenantId, Map<String, Object> parametros) {
        return String.format("tenant:%s:relatorio:%s:hash:%d", 
                tenantId, getTipoRelatorio(), (parametros != null ? parametros.hashCode() : 0));
    }

    protected long getTempoExpiracaoCache() {
        return TTL_CACHE_PADRAO_SEGUNDOS;
    }

    private void validarParametrosObrigatorios(String tenantId, String usuarioId, String formato) {
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalArgumentException("tenantId é obrigatório.");
        }
        if (usuarioId == null || usuarioId.isBlank()) {
            throw new IllegalArgumentException("usuarioId é obrigatório.");
        }
        if (formato == null || formato.isBlank()) {
            throw new IllegalArgumentException("formato de exportação é obrigatório.");
        }
    }

    // Métodos abstratos definidos pelas especializações
    protected abstract Map<String, Object> consolidarMetricas(String tenantId, Map<String, Object> parametros);
    protected abstract String getNomeTemplate();
    protected abstract String getTipoRelatorio();
}
```

---

### 3. Classe Filha 1: `RelatorioVendasMultiTenant`

```java
import java.util.HashMap;
import java.util.Map;

/**
 * Especialização de relatório para consolidação de métricas operacionais e financeiras de vendas.
 */
public class RelatorioVendasMultiTenant extends RelatorioAnalitico {

    private final String regiaoPadrao;
    private final boolean incluirVendasCanceladas;

    public RelatorioVendasMultiTenant(
            DataWarehouseClient dwClient,
            CacheRedis cacheRedis,
            ExportadorPdf exportadorPdf,
            ExportadorCsv exportadorCsv,
            PermissionChecker permissionChecker,
            MotorTemplate motorTemplate,
            EnvioEmailService envioEmailService,
            AgendadorJob agendadorJob,
            LogAudit logAudit,
            String regiaoPadrao,
            boolean incluirVendasCanceladas) {
        super(dwClient, cacheRedis, exportadorPdf, exportadorCsv, permissionChecker, 
              motorTemplate, envioEmailService, agendadorJob, logAudit);
        this.regiaoPadrao = (regiaoPadrao != null) ? regiaoPadrao : "GLOBAL";
        this.incluirVendasCanceladas = incluirVendasCanceladas;
    }

    @Override
    protected Map<String, Object> consolidarMetricas(String tenantId, Map<String, Object> parametros) {
        Map<String, Object> queryParams = new HashMap<>();
        if (parametros != null) {
            queryParams.putAll(parametros);
        }
        queryParams.putIfAbsent("regiao", this.regiaoPadrao);
        queryParams.put("incluirCanceladas", this.incluirVendasCanceladas);

        // Consulta métricas brutas do DW isoladas pelo tenant
        String queryVendas = "SELECT SUM(valor_total) as faturamento, COUNT(id) as total_pedidos, " +
                             "AVG(valor_total) as ticket_medio FROM dw.fato_vendas " +
                             "WHERE tenant_id = :tenantId AND data_venda BETWEEN :dataInicio AND :dataFim";

        Map<String, Object> dadosBrutos = dwClient.executarConsultaMetricas(tenantId, queryVendas, queryParams);

        // Consolidação das métricas
        Map<String, Object> metricasConsolidadas = new HashMap<>(dadosBrutos);
        double faturamento = metricasConsolidadas.containsKey("faturamento") ? 
                Double.parseDouble(metricasConsolidadas.get("faturamento").toString()) : 0.0;
        int totalPedidos = metricasConsolidadas.containsKey("total_pedidos") ? 
                Integer.parseInt(metricasConsolidadas.get("total_pedidos").toString()) : 0;

        metricasConsolidadas.put("tenant_identificador", tenantId);
        metricasConsolidadas.put("ticket_medio_calculado", totalPedidos > 0 ? (faturamento / totalPedidos) : 0.0);
        metricasConsolidadas.put("regiao_analisada", queryParams.get("regiao"));
        metricasConsolidadas.put("vendas_canceladas_inclusas", this.incluirVendasCanceladas);

        return metricasConsolidadas;
    }

    @Override
    protected String getNomeTemplate() {
        return "relatorios/template_vendas_consolidado.html";
    }

    @Override
    protected String getTipoRelatorio() {
        return "VENDAS_MULTITENANT";
    }
}
```

---

### 4. Classe Filha 2: `RelatorioPerformanceMarketing`

```java
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Especialização de relatório para consolidação de métricas de aquisição e campanhas de marketing.
 */
public class RelatorioPerformanceMarketing extends RelatorioAnalitico {

    private final List<String> canaisPadrao;
    private final double metaRoasMinimo;

    public RelatorioPerformanceMarketing(
            DataWarehouseClient dwClient,
            CacheRedis cacheRedis,
            ExportadorPdf exportadorPdf,
            ExportadorCsv exportadorCsv,
            PermissionChecker permissionChecker,
            MotorTemplate motorTemplate,
            EnvioEmailService envioEmailService,
            AgendadorJob agendadorJob,
            LogAudit logAudit,
            List<String> canaisPadrao,
            double metaRoasMinimo) {
        super(dwClient, cacheRedis, exportadorPdf, exportadorCsv, permissionChecker, 
              motorTemplate, envioEmailService, agendadorJob, logAudit);
        this.canaisPadrao = (canaisPadrao != null) ? List.copyOf(canaisPadrao) : List.of("GOOGLE_ADS", "META_ADS");
        this.metaRoasMinimo = metaRoasMinimo;
    }

    @Override
    protected Map<String, Object> consolidarMetricas(String tenantId, Map<String, Object> parametros) {
        Map<String, Object> queryParams = new HashMap<>();
        if (parametros != null) {
            queryParams.putAll(parametros);
        }
        queryParams.putIfAbsent("canais", this.canaisPadrao);

        // Consulta dados consolidados de tráfego e conversão no DW
        String sqlMarketing = "SELECT SUM(investimento) as total_investido, SUM(receita_gerada) as receita_atribuida, " +
                              "SUM(cliques) as total_cliques, SUM(conversoes) as total_leads FROM dw.fato_marketing " +
                              "WHERE tenant_id = :tenantId AND data_campanha BETWEEN :dataInicio AND :dataFim";

        Map<String, Object> dadosCampanhas = dwClient.executarConsultaMetricas(tenantId, sqlMarketing, queryParams);

        Map<String, Object> metricasConsolidadas = new HashMap<>(dadosCampanhas);

        double totalInvestido = metricasConsolidadas.containsKey("total_investido") ? 
                Double.parseDouble(metricasConsolidadas.get("total_investido").toString()) : 0.0;
        double receitaAtribuida = metricasConsolidadas.containsKey("receita_gerada") ? 
                Double.parseDouble(metricasConsolidadas.get("receita_gerada").toString()) : 0.0;
        int totalLeads = metricasConsolidadas.containsKey("total_leads") ? 
                Integer.parseInt(metricasConsolidadas.get("total_leads").toString()) : 0;

        // Cálculos analíticos de ROI, CAC e ROAS
        double roasCalculado = totalInvestido > 0 ? (receitaAtribuida / totalInvestido) : 0.0;
        double cacCalculado = totalLeads > 0 ? (totalInvestido / totalLeads) : 0.0;

        metricasConsolidadas.put("tenant_identificador", tenantId);
        metricasConsolidadas.put("roas_calculado", roasCalculado);
        metricasConsolidadas.put("cac_calculado", cacCalculado);
        metricasConsolidadas.put("meta_roas_minimo", this.metaRoasMinimo);
        metricasConsolidadas.put("atingiu_meta_roas", roasCalculado >= this.metaRoasMinimo);
        metricasConsolidadas.put("canais_monitorados", queryParams.get("canais"));

        return metricasConsolidadas;
    }

    @Override
    protected long getTempoExpiracaoCache() {
        // Relatórios de marketing têm atualização mais frequente (30 minutos)
        return 1800;
    }

    @Override
    protected String getNomeTemplate() {
        return "relatorios/template_marketing_performance.html";
    }

    @Override
    protected String getTipoRelatorio() {
        return "PERFORMANCE_MARKETING";
    }
}
```