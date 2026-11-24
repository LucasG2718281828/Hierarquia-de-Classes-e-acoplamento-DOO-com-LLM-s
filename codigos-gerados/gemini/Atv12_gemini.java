Abaixo está a implementação completa em Java utilizando o padrão de projeto **Template Method**, que organiza o fluxo de checkout garantindo os passos obrigatórios (estoque, validação, CRM, etc.) e permitindo que as subclasses (`PedidoECommerce` e `PedidoB2B`) personalizem etapas como análise de risco/crédito e cobrança.

---

### 1. Stubs das Dependências e Modelo de Dados

```java
// ==========================================
// MODELOS BÁSICOS (STUBS)
// ==========================================
class Cliente {
    private String id;
    private String nome;
    private String email;
    private String documento;

    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getDocumento() { return documento; }
}

class Pedido {
    private String id;
    private Cliente cliente;
    private double valorTotal;

    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
}

// ==========================================
// SERVIÇOS ACOPLADOS (STUBS)
// ==========================================
class CatalogoService {
    public boolean validarDisponibilidade(Pedido pedido) { return true; }
}

class EstoqueService {
    public boolean reservarEstoque(Pedido pedido) { return true; }
    public void liberarEstoque(Pedido pedido) {}
}

class PagamentoGateway {
    public boolean processarPagamento(Pedido pedido) { return true; }
}

class AntiFraudeService {
    public boolean analisarRisco(Pedido pedido) { return true; }
}

class CrmService {
    public void sincronizarPedido(Pedido pedido, String status) {}
}

class FreteCalculator {
    public double calcularFrete(Pedido pedido) { return 0.0; }
}

class EmailDispatcher {
    public void enviarEmail(String destinatario, String assunto, String corpo) {}
}

class NotaFiscalService {
    public String emitirNota(Pedido pedido) { return "NF-0001"; }
}

class AnaliseCredito {
    public boolean verificarLimite(Cliente cliente, double valor) { return true; }
}
```

---

### 2. Classe Base: `ProcessadorPedido`

```java
public abstract class ProcessadorPedido {

    // Dependências acopladas
    protected final CatalogoService catalogoService;
    protected final EstoqueService estoqueService;
    protected final PagamentoGateway pagamentoGateway;
    protected final AntiFraudeService antiFraudeService;
    protected final CrmService crmService;
    protected final FreteCalculator freteCalculator;
    protected final EmailDispatcher emailDispatcher;
    protected final NotaFiscalService notaFiscalService;
    protected final AnaliseCredito analiseCredito;

    public ProcessadorPedido(
            CatalogoService catalogoService,
            EstoqueService estoqueService,
            PagamentoGateway pagamentoGateway,
            AntiFraudeService antiFraudeService,
            CrmService crmService,
            FreteCalculator freteCalculator,
            EmailDispatcher emailDispatcher,
            NotaFiscalService notaFiscalService,
            AnaliseCredito analiseCredito) {
        this.catalogoService = catalogoService;
        this.estoqueService = estoqueService;
        this.pagamentoGateway = pagamentoGateway;
        this.antiFraudeService = antiFraudeService;
        this.crmService = crmService;
        this.freteCalculator = freteCalculator;
        this.emailDispatcher = emailDispatcher;
        this.notaFiscalService = notaFiscalService;
        this.analiseCredito = analiseCredito;
    }

    /**
     * Template Method: Executa o fluxo de checkout orquestrado.
     */
    public final void executarCheckout(Pedido pedido) {
        System.out.println("Iniciando checkout do pedido: " + pedido.getId());

        // 1. Validação de Catálogo
        if (!catalogoService.validarDisponibilidade(pedido)) {
            throw new IllegalStateException("Itens indisponíveis no catálogo.");
        }

        // 2. Cálculo e aplicação de Frete
        double valorFrete = freteCalculator.calcularFrete(pedido);
        pedido.setValorTotal(pedido.getValorTotal() + valorFrete);

        // 3. Reserva de Estoque
        if (!estoqueService.reservarEstoque(pedido)) {
            throw new IllegalStateException("Falha ao reservar estoque.");
        }

        try {
            // 4. Análise de Risco / Crédito (Especializada pelas filhas)
            validarRiscoOuCredito(pedido);

            // 5. Cobrança / Pagamento (Especializada pelas filhas)
            processarCobranca(pedido);

            // 6. Faturamento / Emissão de NF
            String numeroNF = notaFiscalService.emitirNota(pedido);
            System.out.println("Nota Fiscal emitida: " + numeroNF);

            // 7. Atualização no CRM
            crmService.sincronizarPedido(pedido, "APROVADO");

            // 8. Notificação ao Cliente
            emailDispatcher.enviarEmail(
                pedido.getCliente().getEmail(),
                "Pedido Aprovado!",
                "Seu pedido " + pedido.getId() + " foi faturado sob a NF " + numeroNF
            );

        } catch (Exception e) {
            // Rollback de estoque em caso de falha posterior à reserva
            estoqueService.liberarEstoque(pedido);
            crmService.sincronizarPedido(pedido, "CANCELADO_OU_FALHA");
            emailDispatcher.enviarEmail(
                pedido.getCliente().getEmail(),
                "Problema no Pedido",
                "Houve uma falha no processamento: " + e.getMessage()
            );
            throw new RuntimeException("Falha no checkout: " + e.getMessage(), e);
        }
    }

    // Métodos abstratos para especialização nas subclasses
    protected abstract void validarRiscoOuCredito(Pedido pedido);
    protected abstract void processarCobranca(Pedido pedido);
}
```

---

### 3. Subclasses: `PedidoECommerce` e `PedidoB2B`

```java
/**
 * Implementação para Vendas Diretas ao Consumidor (B2C / E-Commerce).
 * Prioriza Anti-Fraude e Pagamento imediato via Gateway.
 */
public class PedidoECommerce extends ProcessadorPedido {

    public PedidoECommerce(
            CatalogoService catalogoService,
            EstoqueService estoqueService,
            PagamentoGateway pagamentoGateway,
            AntiFraudeService antiFraudeService,
            CrmService crmService,
            FreteCalculator freteCalculator,
            EmailDispatcher emailDispatcher,
            NotaFiscalService notaFiscalService,
            AnaliseCredito analiseCredito) {
        super(catalogoService, estoqueService, pagamentoGateway, antiFraudeService,
              crmService, freteCalculator, emailDispatcher, notaFiscalService, analiseCredito);
    }

    @Override
    protected void validarRiscoOuCredito(Pedido pedido) {
        System.out.println("[E-Commerce] Executando validação antifraude...");
        boolean aprovado = this.antiFraudeService.analisarRisco(pedido);
        if (!aprovado) {
            throw new SecurityException("Pedido reprovado pelo serviço de Anti-Fraude.");
        }
    }

    @Override
    protected void processarCobranca(Pedido pedido) {
        System.out.println("[E-Commerce] Processando transação via Gateway de Pagamento...");
        boolean sucesso = this.pagamentoGateway.processarPagamento(pedido);
        if (!sucesso) {
            throw new IllegalStateException("Falha na captura do pagamento no gateway.");
        }
    }
}
```

```java
/**
 * Implementação para Vendas Corporativas (B2B).
 * Prioriza Análise de Linha de Crédito e Faturamento a Prazo.
 */
public class PedidoB2B extends ProcessadorPedido {

    public PedidoB2B(
            CatalogoService catalogoService,
            EstoqueService estoqueService,
            PagamentoGateway pagamentoGateway,
            AntiFraudeService antiFraudeService,
            CrmService crmService,
            FreteCalculator freteCalculator,
            EmailDispatcher emailDispatcher,
            NotaFiscalService notaFiscalService,
            AnaliseCredito analiseCredito) {
        super(catalogoService, estoqueService, pagamentoGateway, antiFraudeService,
              crmService, freteCalculator, emailDispatcher, notaFiscalService, analiseCredito);
    }

    @Override
    protected void validarRiscoOuCredito(Pedido pedido) {
        System.out.println("[B2B] Consultando limite de crédito corporativo...");
        boolean creditoAprovado = this.analiseCredito.verificarLimite(
            pedido.getCliente(),
            pedido.getValorTotal()
        );
        if (!creditoAprovado) {
            throw new IllegalStateException("Cliente B2B sem limite de crédito suficiente.");
        }
    }

    @Override
    protected void processarCobranca(Pedido pedido) {
        System.out.println("[B2B] Gerando faturamento a prazo / borderô de cobrança...");
        // B2B frequentemente não transaciona cartão no gateway imediato, 
        // mas pode registrar a intenção no gateway ou emitir fatura interna.
        boolean faturado = this.pagamentoGateway.processarPagamento(pedido);
        if (!faturado) {
            throw new IllegalStateException("Erro ao registrar faturamento corporativo.");
        }
    }
}
```