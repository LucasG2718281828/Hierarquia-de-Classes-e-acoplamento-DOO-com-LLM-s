package br.edu.ifsudestemg.doo.gemini.Pagamento;

public abstract class MeioDePagamento {
    protected double valor;
    protected Moeda moeda;
    protected String descricao;

    protected GatewayPagamento gatewayPagamento;
    protected ValidadorFraude validadorFraude;
    protected ReciboFactory reciboFactory;
    protected Notificacao notificacao;

    public MeioDePagamento(double valor, Moeda moeda, String descricao,
                           GatewayPagamento gatewayPagamento,
                           ValidadorFraude validadorFraude,
                           ReciboFactory reciboFactory,
                           Notificacao notificacao) {
        this.valor = valor;
        this.moeda = moeda;
        this.descricao = descricao;
        this.gatewayPagamento = gatewayPagamento;
        this.validadorFraude = validadorFraude;
        this.reciboFactory = reciboFactory;
        this.notificacao = notificacao;
    }

    public boolean efetuarPagamento() {
        PagamentoInfo info = new PagamentoInfo(valor, moeda, descricao, this.getTipo());
        if (!validadorFraude.verificar(info)) {
            notificacao.enviar("Pagamento rejeitado por suspeita de fraude.");
            return false;
        }

        boolean processado = gatewayPagamento.processar(info);
        if (!processado) {
            notificacao.enviar("Falha ao processar o pagamento.");
            return false;
        }

        Recibo recibo = reciboFactory.gerarRecibo(info);
        recibo.imprimir();

        notificacao.enviar("Pagamento efetuado com sucesso. Recibo enviado.");
        return true;
    }

    protected abstract String getTipo();
}