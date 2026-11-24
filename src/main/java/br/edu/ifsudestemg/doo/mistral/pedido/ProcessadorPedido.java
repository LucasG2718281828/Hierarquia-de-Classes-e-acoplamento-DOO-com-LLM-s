package br.edu.ifsudestemg.doo.mistral.pedido;

public class ProcessadorPedido {
    protected CatalogoService catalogoService;
    protected EstoqueService estoqueService;
    protected PagamentoGateway pagamentoGateway;
    protected AntiFraudeService antiFraudeService;
    protected CrmService crmService;
    protected FreteCalculator freteCalculator;
    protected EmailDispatcher emailDispatcher;
    protected NotaFiscalService notaFiscalService;
    protected AnaliseCredito analiseCredito;

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

    public void executarCheckout(Pedido pedido) {
        // Reserva de estoque
        reserveStock(pedido);

        // Verificação antifraude
        if (!antiFraudeService.verificar(pedido)) {
            throw new RuntimeException("Fraude detectada no pedido " + pedido.getId());
        }

        // Processamento de pagamento
        pagamentoGateway.processar(pedido);

        // Atualização do CRM
        crmService.atualizar(pedido);

        // Cálculo de frete
        double frete = freteCalculator.calcular(pedido);
        pedido.setFrete(frete);

        // Geração de nota fiscal
        notaFiscalService.gerar(pedido);

        // Envio de e‑mail de confirmação
        emailDispatcher.enviar(pedido);

        // Análise de crédito
        analiseCredito.analisar(pedido);
    }

    protected void reserveStock(Pedido pedido) {
        estoqueService.reservar(pedido);
    }
}