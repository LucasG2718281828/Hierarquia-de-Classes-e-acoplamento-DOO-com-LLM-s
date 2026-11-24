package br.edu.ifsudestemg.doo.mistral.pedido;

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
        // Validação de endereço de entrega específica para e‑commerce
        if (!pedido.getEnderecoEntrega().isValido()) {
            throw new RuntimeException("Endereço de entrega inválido para e‑commerce");
        }
        super.executarCheckout(pedido);
    }
}