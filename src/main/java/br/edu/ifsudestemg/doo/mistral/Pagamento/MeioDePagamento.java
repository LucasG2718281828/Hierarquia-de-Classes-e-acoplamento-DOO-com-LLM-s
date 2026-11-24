package br.edu.ifsudestemg.doo.mistral.Pagamento;

public abstract class MeioDePagamento {
    protected GatewayPagamento gateway;
    protected ValidadorFraude validador;
    protected Notificacao notificacao;

    public MeioDePagamento(GatewayPagamento gateway, ValidadorFraude validador, Notificacao notificacao) {
        this.gateway = gateway;
        this.validador = validador;
        this.notificacao = notificacao;
    }

    public abstract boolean efetuarPagamento(double valor, Moeda moeda);

    public Recibo emitirRecibo(double valor, Moeda moeda) {
        String numeroRecibo = "REC-" + System.currentTimeMillis();
        Recibo recibo = new Recibo(numeroRecibo, valor, moeda);
        notificacao.enviar("Recibo emitido: " + numeroRecibo);
        return recibo;
    }
}