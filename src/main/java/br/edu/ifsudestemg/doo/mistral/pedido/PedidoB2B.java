package br.edu.ifsudestemg.doo.mistral.pedido;

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
        // Em B2B, pode ser necessário validar contrato de compra
        if (!pedido.getContrato().isValido()) {
            throw new RuntimeException("Contrato inválido para pedido B2B");
        }
        super.executarCheckout(pedido);
    }
}