package br.edu.ifsudestemg.doo.mistral.FormaGeometrica;

public abstract class FormaGeometrica {
    private Ponto2D posicao;
    private Cor cor;

    public FormaGeometrica(Ponto2D posicao, Cor cor) {
        this.posicao = posicao;
        this.cor = cor;
    }

    public abstract double calcularArea();

    public abstract void desenhar();

    public Ponto2D getPosicao() {
        return posicao;
    }

    public void setPosicao(Ponto2D posicao) {
        this.posicao = posicao;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }
}