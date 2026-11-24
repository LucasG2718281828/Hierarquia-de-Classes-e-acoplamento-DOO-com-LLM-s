package br.edu.ifsudestemg.doo.gemini.Fiscal;

public class ItemTributado {
    private String descricao;
    private double quantidade;
    private double valorUnitario;
    private double aliquota;

    // Construtor, getters e setters
    public ItemTributado(String descricao, double quantidade, double valorUnitario, double aliquota) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.aliquota = aliquota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getAliquota() {
        return aliquota;
    }

    public void setAliquota(double aliquota) {
        this.aliquota = aliquota;
    }
}