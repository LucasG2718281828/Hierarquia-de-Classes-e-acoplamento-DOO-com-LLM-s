package br.edu.ifsudestemg.doo.gemini.pedido;

import java.util.List;

public class Pedido {
    private String id;
    private String clienteId;
    private List<ItemPedido> itens;
    private double valorTotal;
    private String enderecoEntrega;

    public Pedido(String id, String clienteId, List<ItemPedido> itens, double valorTotal, String enderecoEntrega) {
        this.id = id;
        this.clienteId = clienteId;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.enderecoEntrega = enderecoEntrega;
    }

    public String getId() {
        return id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }
}