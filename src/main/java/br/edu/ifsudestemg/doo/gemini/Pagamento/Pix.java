package br.edu.ifsudestemg.doo.gemini.Pagamento;

public class Pix extends MeioDePagamento {
    private String chavePix;

    public Pix(double valor, Moeda moeda, String descricao,
               String chavePix,
               GatewayPagamento gatewayPagamento,
               ValidadorFraude validadorFraude,
               ReciboFactory reciboFactory,
               Notificacao notificacao) {
        super(valor, moeda, descricao, gatewayPagamento, validadorFraude, reciboFactory, notificacao);
        this.chavePix = chavePix;
    }

    @Override
    protected String getTipo() {
        return "PIX";
    }

    // Getters e Setters (opcional)
}