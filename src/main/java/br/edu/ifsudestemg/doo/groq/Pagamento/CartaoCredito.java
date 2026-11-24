package br.edu.ifsudestemg.doo.groq.Pagamento;

public class CartaoCredito extends MeioDePagamento {
    private String numeroCartao;
    private String validade;
    private String cvv;

    public CartaoCredito(String numeroCartao,
                         String validade,
                         String cvv,
                         GatewayPagamento gateway,
                         ValidadorFraude validador,
                         Recibo recibo,
                         Notificacao notificacao,
                         Moeda moeda) {
        super(gateway, validador, recibo, notificacao, moeda);
        this.numeroCartao = numeroCartao;
        this.validade = validade;
        this.cvv = cvv;
    }

    @Override
    public void efetuarPagamento(double valor, String descricao) {
        // Pode adicionar lógica específica de cartão, se necessário
        super.efetuarPagamento(valor, descricao);
    }
}