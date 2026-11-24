```java
/*======================================================================
 *  Relatório Analítico – Hierarquia Principal
 *======================================================================
 *  RelatorioAnalitico (classe abstrata)
 *  ├─ RelatorioVendasMultiTenant
 *  └─ RelatorioPerformanceMarketing
 *
 *  Dependências (stubs) – apenas a estrutura mínima necessária para
 *  compilar o código.
 *======================================================================
 */

import java.util.*;

// ---------------------------------------------------------------------
//  Dependências – stubs
// ---------------------------------------------------------------------

/** Cliente que consulta o Data Warehouse. */
class DataWarehouseClient {
    public Map<String, Object> fetchMetrics(List<String> tenantIds) {
        // Simulação de retorno
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("sales", 12345.67);
        metrics.put("clicks", 9876);
        return metrics;
    }
}

/** Cache Redis simplificado. */
class CacheRedis {
    private final Map<String, Map<String, Object>> store = new HashMap<>();

    public Map<String, Object> get(String key) {
        return store.get(key);
    }

    public void set(String key, Map<String, Object> value) {
        store.put(key, value);
    }
}

/** Exportador PDF. */
class ExportadorPdf {
    public byte[] export(Map<String, Object> data) {
        // Simulação de exportação
        return ("PDF:" + data.toString()).getBytes();
    }
}

/** Exportador CSV. */
class ExportadorCsv {
    public byte[] export(Map<String, Object> data) {
        // Simulação de exportação
        return ("CSV:" + data.toString()).getBytes();
    }
}

/** Verificador de permissões. */
class PermissionChecker {
    public boolean hasPermission(String permission) {
        // Simulação: todas as permissões são concedidas
        return true;
    }
}

/** Motor de templates (ex.: Freemarker, Thymeleaf). */
class MotorTemplate {
    // Placeholder – nenhuma implementação necessária para o exemplo
}

/** Serviço de envio de e‑mail. */
class EnvioEmailService {
    public void sendEmail(String to, String subject, String body,
                          byte[] attachment, String format) {
        // Simulação de envio
        System.out.println("Enviando e‑mail para " + to + " com anexo " + format);
    }
}

/** Agendador de jobs (ex.: Quartz). */
class AgendadorJob {
    public void schedule(String cronExpression, Runnable task) {
        // Simulação de agendamento
        System.out.println("Job agendado com cron: " + cronExpression);
        // Executa imediatamente para fins de demonstração
        task.run();
    }
}

/** Log de auditoria. */
class LogAudit {
    public void log(String message) {
        System.out.println("[AUDIT] " + message);
    }
}

// ---------------------------------------------------------------------
//  Hierarquia de Relatórios
// ---------------------------------------------------------------------

/**
 * Classe abstrata que encapsula a lógica comum de relatórios analíticos.
 */
abstract class RelatorioAnalitico {

    /* Dependências */
    protected final DataWarehouseClient dwClient;
    protected final CacheRedis cacheRedis;
    protected final ExportadorPdf exportadorPdf;
    protected final ExportadorCsv exportadorCsv;
    protected final PermissionChecker permissionChecker;
    protected final MotorTemplate motorTemplate;
    protected final EnvioEmailService envioEmailService;
    protected final AgendadorJob agendadorJob;
    protected final LogAudit logAudit;

    /* -----------------------------------------------------------------
     *  Construtor
     * ----------------------------------------------------------------- */
    public RelatorioAnalitico(DataWarehouseClient dwClient,
                               CacheRedis cacheRedis,
                               ExportadorPdf exportadorPdf,
