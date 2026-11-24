package br.edu.ifsudestemg.doo.mistral.dispostitivosEletronicos;

public class Bateria {
    private int capacidade; // em mAh
    private int ciclos;

    public Bateria(int capacidade, int ciclos) {
        this.capacidade = capacidade;
        this.ciclos = ciclos;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getCiclos() {
        return ciclos;
    }

    public void setCiclos(int ciclos) {
        this.ciclos = ciclos;
    }

    public void carregar() {
        // Método vazio para simular carga
    }

    public void descarregar() {
        // Método vazio para simular descarga
    }
}