package br.edu.ifsudestemg.doo.gemini.Pagamento;

public class PagamentoInfo {
    private double valor;
    private Moeda moeda;
    private String descricao;
    private String tipoMeioPagamento;

    public PagamentoInfo(double valor, Moeda moeda, String descricao, String tipoMeioPagamento) {
        this.valor = valor;
        this.moeda = moeda;
        this.descricao = descricao;
        this.tipoMeioPagamento = tipoMeioPagamento;
    }

    public double getValor() {
        return valor;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipoMeioPagamento() {
        return tipoMeioPagamento;
    }
}