package br.edu.ifsudestemg.doo.groq.veiculo;

public class Motor {
    private double potencia; // em HP

    public Motor(double potencia) {
        this.potencia = potencia;
    }

    public double getPotencia() {
        return potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }
}