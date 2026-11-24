package br.edu.ifsudestemg.doo.mistral.Pagamento;

public class CartaoCredito extends MeioDePagamento {
    private String numeroCartao;
    private String titular;
    private int validade;

    public CartaoCredito(String numeroCartao, String titular, int validade, GatewayPagamento gateway, ValidadorFraude validador, Notificacao notificacao) {
        super(gateway, validador, notificacao);
        this.numeroCartao = numeroCartao;
        this.titular = titular;
        this.validade = validade;
    }

    @Override
    public boolean efetuarPagamento(double valor, Moeda moeda) {
        boolean fraudeOk = validador.validar(numeroCartao, valor);
        if (!fraudeOk) {
            notificacao.enviar("Pagamento recusado por suspeita de fraude.");
            return false;
        }
        boolean processado = gateway.processarTransacao(valor, moeda);
        if (processado) {
            emitirRecibo(valor, moeda);
            notificacao.enviar("Pagamento com cartão de crédito aprovado.");
        } else {
            notificacao.enviar("Falha no processamento do cartão de crédito.");
        }
        return processado;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public int getValidade() {
        return validade;
    }

    public void setValidade(int validade) {
        this.validade = validade;
    }
}