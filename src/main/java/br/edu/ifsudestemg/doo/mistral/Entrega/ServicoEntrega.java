package br.edu.ifsudestemg.doo.mistral.Entrega;

public class ServicoEntrega {

    protected RoteirizadorGps roteirizadorGps;
    protected Rastreador rastreador;
    protected SeguroCarga seguroCarga;
    protected Transportadora transportadora;
    protected TabelaFrete tabelaFrete;
    protected NotificadorSms notificadorSms;
    protected Aduana aduana;
    protected CalculadorPrazo calculadorPrazo;

    public ServicoEntrega(RoteirizadorGps roteirizadorGps,
                          Rastreador rastreador,
                          SeguroCarga seguroCarga,
                          Transportadora transportadora,
                          TabelaFrete tabelaFrete,
                          NotificadorSms notificadorSms,
                          Aduana aduana,
                          CalculadorPrazo calculadorPrazo) {
        this.roteirizadorGps = roteirizadorGps;
        this.rastreador = rastreador;
        this.seguroCarga = seguroCarga;
        this.transportadora = transportadora;
        this.tabelaFrete = tabelaFrete;
        this.notificadorSms = notificadorSms;
        this.aduana = aduana;
        this.calculadorPrazo = calculadorPrazo;
    }

    public void orquestrarDespachoExpresso(Entrega entrega) {
        // Roteirização GPS
        String rota = roteirizadorGps.roteirizar(entrega);
        // Contratação de seguro
        boolean seguroContratado = seguroCarga.contratarSeguro(entrega);
        // Cálculo de frete
        double valorFrete = tabelaFrete.calcularFrete(entrega);
        // Envio pela transportadora
        boolean enviado = transportadora.enviar(entrega);
        // Rastreamento
        String status = rastreador.rastrear(entrega);
        // Notificação SMS
        notificadorSms.enviarSms("Despacho expresso iniciado. Rota: " + rota + ", Seguro: " + seguroContratado + ", Frete: R$" + valorFrete + ", Status: " + status);
    }

    public void orquestrarDespachoInternacional(Entrega entrega) {
        // Processamento aduaneiro
        aduana.processarDespacho(entrega);
        // Cálculo de prazo
        int prazoDias = calculadorPrazo.calcularPrazo(entrega);
        // Roteirização GPS
        String rota = roteirizadorGps.roteirizar(entrega);
        // Contratação de seguro
        boolean seguroContratado = seguroCarga.contratarSeguro(entrega);
        // Cálculo de frete
        double valorFrete = tabelaFrete.calcularFrete(entrega);
        // Envio pela transportadora
        boolean enviado = transportadora.enviar(entrega);
        // Rastreamento
        String status = rastreador.rastrear(entrega);
        // Notificação SMS
        notificadorSms.enviarSms("Despacho internacional iniciado. Rota: " + rota + ", Seguro: " + seguroContratado + ", Frete: R$" + valorFrete + ", Prazo: " + prazoDias + " dias, Status: " + status);
    }
}