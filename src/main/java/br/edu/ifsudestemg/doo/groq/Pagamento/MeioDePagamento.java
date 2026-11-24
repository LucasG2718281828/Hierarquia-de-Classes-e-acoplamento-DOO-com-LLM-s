package br.edu.ifsudestemg.doo.groq.Pagamento;

public abstract class MeioDePagamento {
    protected GatewayPagamento gateway;
    protected ValidadorFraude validador;
    protected Recibo recibo;
    protected Notificacao notificacao;
    protected Moeda moeda;

    public MeioDePagamento(GatewayPagamento gateway,
                           ValidadorFraude validador,
                           Recibo recibo,
                           Notificacao notificacao,
                           Moeda moeda) {
        this.gateway = gateway;
        this.validador = validador;
        this.recibo = recibo;
        this.notificacao = notificacao;
        this.moeda = moeda;
    }

    public void efetuarPagamento(double valor, String descricao) {
        // Verificação antifraude
        if (!validador.verificarFraude(valor)) {
            throw new RuntimeException("Fraude detectada. Pagamento cancelado.");
        }

        // Processamento do pagamento
        gateway.processarPagamento(valor);

        // Emissão de recibo
        recibo.emitir(valor, descricao);

        // Notificação ao cliente
        notificacao.enviar("Pagamento de " + moeda.formatar(valor) + " efetuado com sucesso.");
    }
}