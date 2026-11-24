package br.edu.ifsudestemg.doo.gemini.Entrega;

public class EntregaInternacional extends ServicoEntrega {

    public EntregaInternacional(RoteirizadorGps roteirizador,
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
        // Processamento aduaneiro
        aduana.processar(pedido);
        // Notificação ao cliente sobre a liberação alfandegária
        String msg = String.format("Entrega Internacional: seu pedido %s está em processo de liberação alfandegária.", pedido.getId());
        notificadorSms.enviar(pedido.getClienteTelefone(), msg);
    }
}