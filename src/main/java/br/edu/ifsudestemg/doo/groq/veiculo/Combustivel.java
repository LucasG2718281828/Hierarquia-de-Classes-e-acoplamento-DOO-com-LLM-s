package br.edu.ifsudestemg.doo.groq.veiculo;

public class Combustivel {
    private double capacidade; // em litros

    public Combustivel(double capacidade) {
        this.capacidade = capacidade;
    }

    public double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(double capacidade) {
        this.capacidade = capacidade;
    }
}