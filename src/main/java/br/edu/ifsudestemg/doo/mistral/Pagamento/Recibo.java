package br.edu.ifsudestemg.doo.mistral.Pagamento;

public class Recibo {
    private String numero;
    private double valor;
    private Moeda moeda;

    public Recibo(String numero, double valor, Moeda moeda) {
        this.numero = numero;
        this.valor = valor;
        this.moeda = moeda;
    }

    public String getNumero() {
        return numero;
    }

    public double getValor() {
        return valor;
    }

    public Moeda getMoeda() {
        return moeda;
    }
}