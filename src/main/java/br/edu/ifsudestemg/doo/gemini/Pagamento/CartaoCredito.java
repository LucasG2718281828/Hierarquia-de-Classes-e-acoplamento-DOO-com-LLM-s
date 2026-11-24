package br.edu.ifsudestemg.doo.gemini.Pagamento;

public class CartaoCredito extends MeioDePagamento {
    private String numeroCartao;
    private String nomeTitular;
    private String validade;
    private String cvv;

    public CartaoCredito(double valor, Moeda moeda, String descricao,
                         String numeroCartao, String nomeTitular,
                         String validade, String cvv,
                         GatewayPagamento gatewayPagamento,
                         ValidadorFraude validadorFraude,
                         ReciboFactory reciboFactory,
                         Notificacao notificacao) {
        super(valor, moeda, descricao, gatewayPagamento, validadorFraude, reciboFactory, notificacao);
        this.numeroCartao = numeroCartao;
        this.nomeTitular = nomeTitular;
        this.validade = validade;
        this.cvv = cvv;
    }

    @Override
    protected String getTipo() {
        return "Cartão de Crédito";
    }

    // Getters e Setters (opcional)
}