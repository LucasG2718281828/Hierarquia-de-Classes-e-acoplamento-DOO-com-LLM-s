package br.edu.ifsudestemg.doo.groq.Pagamento;

public class Pix extends MeioDePagamento {
    private String chavePix;

    public Pix(String chavePix,
               GatewayPagamento gateway,
               ValidadorFraude validador,
               Recibo recibo,
               Notificacao notificacao,
               Moeda moeda) {
        super(gateway, validador, recibo, notificacao, moeda);
        this.chavePix = chavePix;
    }

    @Override
    public void efetuarPagamento(double valor, String descricao) {
        // Pode adicionar lógica específica de Pix, se necessário
        super.efetuarPagamento(valor, descricao);
    }
}