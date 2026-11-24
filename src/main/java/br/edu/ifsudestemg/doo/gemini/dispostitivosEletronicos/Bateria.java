package br.edu.ifsudestemg.doo.gemini.dispostitivosEletronicos;

public class Bateria {
    private int capacidade; // em mAh
    private int nivelAtual; // percentual 0-100

    public Bateria(int capacidade, int nivelInicial) {
        this.capacidade = capacidade;
        this.nivelAtual = Math.min(100, Math.max(0, nivelInicial));
    }

    public void carregar(int percentual) {
        // implementação vazia (stub)
    }

    public void descarregar(int percentual) {
        // implementação vazia (stub)
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getNivelAtual() {
        return nivelAtual;
    }

    public void setNivelAtual(int nivelAtual) {
        this.nivelAtual = nivelAtual;
    }
}