package br.edu.ifsudestemg.doo.mistral.FormaGeometrica;

public class Retangulo extends FormaGeometrica {
    private double largura;
    private double altura;

    public Retangulo(Ponto2D posicao, Cor cor, double largura, double altura) {
        super(posicao, cor);
        this.largura = largura;
        this.altura = altura;
    }

    @Override
    public double calcularArea() {
        return largura * altura;
    }

    @Override
    public void desenhar() {
        System.out.println("Desenhando Retangulo de area " + calcularArea() + " na posicao (" + getPosicao().getX() + ", " + getPosicao().getY() + ") com cor " + getCor().getNome());
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }
}