package br.edu.ifsudestemg.doo.gemini.pedido;

public class PedidoB2B extends ProcessadorPedido {

    public PedidoB2B(CatalogoService catalogoService,
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
    protected void validarAntiFraude(Pedido pedido) {
        // Em B2B o antifraude pode ser menos rigoroso ou até omitido
        // Deixamos vazio ou podemos registrar log
    }

    @Override
    protected void analisarCredito(Pedido pedido) {
        // B2B costuma ter análise de crédito diferenciada
        analiseCredito.analisarB2B(pedido.getClienteId(), pedido.getValorTotal());
    }
}