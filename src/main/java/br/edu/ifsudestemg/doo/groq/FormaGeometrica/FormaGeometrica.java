package br.edu.ifsudestemg.doo.groq.FormaGeometrica;

public abstract class FormaGeometrica {
    private Ponto2D centro;
    private Cor cor;

    public FormaGeometrica(Ponto2D centro, Cor cor) {
        this.centro = centro;
        this.cor = cor;
    }

    public Ponto2D getCentro() {
        return centro;
    }

    public void setCentro(Ponto2D centro) {
        this.centro = centro;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public abstract double calcularArea();

    public abstract void desenhar();
}