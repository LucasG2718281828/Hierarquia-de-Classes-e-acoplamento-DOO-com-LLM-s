package br.edu.ifsudestemg.doo.gemini.pedido;

public class PedidoECommerce extends ProcessadorPedido {

    public PedidoECommerce(CatalogoService catalogoService,
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

    // No e‑commerce o fluxo padrão é suficiente; caso queira customizar, sobrescreva aqui.
}