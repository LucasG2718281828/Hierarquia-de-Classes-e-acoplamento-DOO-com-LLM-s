package br.edu.ifsudestemg.doo.groq.pedido;

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
    public void executarCheckout(Pedido pedido) {
        // Lógica específica para B2B, por exemplo, omitir verificação antifraude
        super.executarCheckout(pedido);
    }
}