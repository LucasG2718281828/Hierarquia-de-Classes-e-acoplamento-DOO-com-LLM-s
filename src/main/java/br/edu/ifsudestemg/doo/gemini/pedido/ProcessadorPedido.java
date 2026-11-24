package br.edu.ifsudestemg.doo.gemini.pedido;

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

    public ProcessadorPedido(CatalogoService catalogoService,
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

    public final void processar(Pedido pedido) {
        validarCatalogo(pedido);
        reservarEstoque(pedido);
        analisarCredito(pedido);
        validarAntiFraude(pedido);
        calcularFrete(pedido);
        processarPagamento(pedido);
        registrarCrm(pedido);
        gerarNotaFiscal(pedido);
        enviarEmailConfirmacao(pedido);
    }

    protected void validarCatalogo(Pedido pedido) {
        // Verifica se todos os produtos existem no catálogo
        catalogoService.verificarProdutos(pedido.getItens());
    }

    protected void reservarEstoque(Pedido pedido) {
        estoqueService.reservar(pedido.getItens());
    }

    protected void analisarCredito(Pedido pedido) {
        analiseCredito.analisar(pedido.getClienteId(), pedido.getValorTotal());
    }

    protected void validarAntiFraude(Pedido pedido) {
        antiFraudeService.analisar(pedido);
    }

    protected void calcularFrete(Pedido pedido) {
        double frete = freteCalculator.calcular(pedido.getEnderecoEntrega(), pedido.getItens());
        // poderia atualizar o pedido, mas mantemos simples
    }

    protected void processarPagamento(Pedido pedido) {
        pagamentoGateway.processar(pedido);
    }

    protected void registrarCrm(Pedido pedido) {
        crmService.registrar(pedido);
    }

    protected void gerarNotaFiscal(Pedido pedido) {
        notaFiscalService.emitir(pedido);
    }

    protected void enviarEmailConfirmacao(Pedido pedido) {
        emailDispatcher.enviarConfirmacao(pedido);
    }
}