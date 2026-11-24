package br.edu.ifsudestemg.doo.groq.pedido;

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

    @Override
    public void executarCheckout(Pedido pedido) {
        // Pode adicionar lógica específica para e‑commerce, por exemplo, validação de endereço
        super.executarCheckout(pedido);
    }
}