package br.edu.ifsudestemg.doo.groq.FormaGeometrica;

public class Retangulo extends FormaGeometrica {
    private double largura;
    private double altura;

    public Retangulo(Ponto2D centro, Cor cor, double largura, double altura) {
        super(centro, cor);
        this.largura = largura;
        this.altura = altura;
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

    @Override
    public double calcularArea() {
        return largura * altura;
    }

    @Override
    public void desenhar() {
        System.out.println("Desenhando retângulo de área " + calcularArea() + " na cor " + getCor());
    }
}