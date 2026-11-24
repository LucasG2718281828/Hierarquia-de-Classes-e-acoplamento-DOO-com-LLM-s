package br.edu.ifsudestemg.doo.gemini.pedido;

public class ItemPedido {
    private String produtoId;
    private int quantidade;
    private double precoUnitario;

    public ItemPedido(String produtoId, int quantidade, double precoUnitario) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public String getProdutoId() {
        return produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }
}