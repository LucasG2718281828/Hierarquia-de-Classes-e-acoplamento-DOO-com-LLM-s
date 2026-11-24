package br.edu.ifsudestemg.doo.groq.Entrega;

public class EntregaExpressa extends ServicoEntrega {

    public EntregaExpressa(RoteirizadorGps roteirizadorGps, Rastreador rastreador, SeguroCarga seguroCarga,
                           Transportadora transportadora, TabelaFrete tabelaFrete, NotificadorSms notificadorSms,
                           Aduana aduana, CalculadorPrazo calculadorPrazo) {
        super(roteirizadorGps, rastreador, seguroCarga, transportadora, tabelaFrete, notificadorSms, aduana, calculadorPrazo);
    }

    public void orquestrarEntregaExpressa(String origem, String destino, double peso) {
        orquestrarDespachoExpresso(origem, destino, peso);
    }
}