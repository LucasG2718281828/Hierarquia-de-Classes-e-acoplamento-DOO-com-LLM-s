**Java – Hierarquia de Processador de Pedidos**

```java
// ------------------------------------------------------------
// 1. Entidades de domínio (Pedido, Item)
// ------------------------------------------------------------
import java.util.List;

public class Pedido {
    private String id;
    private String clienteId;
    private List<Item> itens;

    public Pedido(String id, String clienteId, List<Item> itens) {
        this.id = id;
        this.clienteId = clienteId;
        this.itens = itens;
    }

    public String getId() { return id; }
    public String getClienteId() { return clienteId; }
    public List<Item> getItens() { return itens; }
}

public class Item {
    private String produtoId;
    private int quantidade;

    public Item(String produtoId, int quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public String getProdutoId() { return produtoId; }
    public int getQuantidade() { return quantidade; }
}
```

```java
// ------------------------------------------------------------
// 2. Dependências – stubs (só estrutura necessária)
// ------------------------------------------------------------
public class CatalogoService {
    public void buscar(String produtoId) { /* stub */ }
}

public class EstoqueService {
    public void reservar(Pedido pedido) { /* stub */ }
}

public class PagamentoGateway {
    public void processar(Pedido pedido) { /* stub */ }
}

public class AntiFraudeService {
    public boolean verificar(Pedido pedido) { return true; /* stub */ }
}

public class CrmService {
    public void atualizar(Pedido pedido) { /* stub */ }
}

public class FreteCalculator {
    public void calcular(Pedido pedido) { /* stub */ }
}

public class EmailDispatcher {
    public void enviar(Pedido pedido) { /* stub */ }
}

public class NotaFiscalService {
    public void emitir(Pedido pedido) { /* stub */ }
}

public class AnaliseCredito {
    public void analisar(Pedido pedido) { /* stub */ }
}
```

```java
// ------------------------------------------------------------
// 3. Classe Pai – ProcessadorPedido
// ------------------------------------------------------------
public abstract class ProcessadorPedido {

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

    /** Executa todo o fluxo de checkout */
    public void executarCheckout(Pedido pedido) {
        reservarEstoque(pedido);
        if (!verificarFraude(pedido)) {
            throw new RuntimeException("Fra