package br.edu.ifsudestemg.doo.gemini.Entrega;

public class EntregaExpressa extends ServicoEntrega {

    public EntregaExpressa(RoteirizadorGps roteirizador,
                           Rastreador rastreador,
                           SeguroCarga seguroCarga,
                           Transportadora transportadora,
                           TabelaFrete tabelaFrete,
                           NotificadorSms notificadorSms,
                           Aduana aduana,
                           CalculadorPrazo calculadorPrazo) {
        super(roteirizador, rastreador, seguroCarga, transportadora,
              tabelaFrete, notificadorSms, aduana, calculadorPrazo);
    }

    @Override
    public void orquestrarDespachoExpressa(Pedido pedido) {
        // Execução padrão
        super.orquestrarDespachoExpressa(pedido);
        // Notificação adicional de prioridade expressa
        String extra = String.format("Entrega Expressa: seu pedido %s tem prioridade máxima.", pedido.getId());
        notificadorSms.enviar(pedido.getClienteTelefone(), extra);
    }
}