package br.edu.ifsudestemg.doo.mistral.veiculo;

public class Motor {
    private String modelo;
    private double potencia;

    public Motor(String modelo, double potencia) {
        this.modelo = modelo;
        this.potencia = potencia;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPotencia() {
        return potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }

    @Override
    public String toString() {
        return "Motor{" +
                "modelo='" + modelo + '\'' +
                ", potencia=" + potencia +
                '}';
    }
}