package br.edu.ifsudestemg.doo.groq.FormaGeometrica;

public class Circulo extends FormaGeometrica {
    private double raio;

    public Circulo(Ponto2D centro, Cor cor, double raio) {
        super(centro, cor);
        this.raio = raio;
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    @Override
    public double calcularArea() {
        return Math.PI * raio * raio;
    }

    @Override
    public void desenhar() {
        System.out.println("Desenhando círculo de área " + calcularArea() + " na cor " + getCor());
    }
}