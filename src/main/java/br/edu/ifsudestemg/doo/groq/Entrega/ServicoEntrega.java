package br.edu.ifsudestemg.doo.groq.Entrega;

public class ServicoEntrega {
    protected RoteirizadorGps roteirizadorGps;
    protected Rastreador rastreador;
    protected SeguroCarga seguroCarga;
    protected Transportadora transportadora;
    protected TabelaFrete tabelaFrete;
    protected NotificadorSms notificadorSms;
    protected Aduana aduana;
    protected CalculadorPrazo calculadorPrazo;

    public ServicoEntrega(RoteirizadorGps roteirizadorGps, Rastreador rastreador, SeguroCarga seguroCarga,
                          Transportadora transportadora, TabelaFrete tabelaFrete, NotificadorSms notificadorSms,
                          Aduana aduana, CalculadorPrazo calculadorPrazo) {
        this.roteirizadorGps = roteirizadorGps;
        this.rastreador = rastreador;
        this.seguroCarga = seguroCarga;
        this.transportadora = transportadora;
        this.tabelaFrete = tabelaFrete;
        this.notificadorSms = notificadorSms;
        this.aduana = aduana;
        this.calculadorPrazo = calculadorPrazo;
    }

    public void orquestrarDespachoExpresso(String origem, String destino, double peso) {
        String rota = roteirizadorGps.gerarRota(origem, destino);
        double valorFrete = tabelaFrete.calcularFrete(peso, rota);
        double valorSeguro = seguroCarga.calcularSeguro(peso, rota);
        transportadora.registrarTransporte(origem, destino, rota, peso);
        notificadorSms.enviarSms("Despacho expresso orquestrado: " + rota + ", Frete: " + valorFrete + ", Seguro: " + valorSeguro);
    }

    public void orquestrarDespachoInternacional(String origem, String destino, double peso, String paisDestino) {
        aduana.processarDespacho(origem, destino, paisDestino);
        String rota = roteirizadorGps.gerarRota(origem, destino);
        double valorFrete = tabelaFrete.calcularFrete(peso, rota);
        double valorSeguro = seguroCarga.calcularSeguro(peso, rota);
        transportadora.registrarTransporte(origem, destino, rota, peso);
        notificadorSms.enviarSms("Despacho internacional orquestrado: " + rota + ", Frete: " + valorFrete + ", Seguro: " + valorSeguro);
    }
}