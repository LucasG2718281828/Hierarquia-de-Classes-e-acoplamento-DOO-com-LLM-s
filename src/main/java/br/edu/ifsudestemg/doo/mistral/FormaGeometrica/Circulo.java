package br.edu.ifsudestemg.doo.mistral.FormaGeometrica;

public class Circulo extends FormaGeometrica {
    private double raio;

    public Circulo(Ponto2D posicao, Cor cor, double raio) {
        super(posicao, cor);
        this.raio = raio;
    }

    @Override
    public double calcularArea() {
        return Math.PI * raio * raio;
    }

    @Override
    public void desenhar() {
        System.out.println("Desenhando Circulo de area " + calcularArea() + " na posicao (" + getPosicao().getX() + ", " + getPosicao().getY() + ") com cor " + getCor().getNome());
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }
}