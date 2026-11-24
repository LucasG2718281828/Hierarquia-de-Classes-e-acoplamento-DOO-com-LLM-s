package br.edu.ifsudestemg.doo.mistral.veiculo;

public class Combustivel {
    private String tipo;
    private double capacidadeTanque;

    public Combustivel(String tipo, double capacidadeTanque) {
        this.tipo = tipo;
        this.capacidadeTanque = capacidadeTanque;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getCapacidadeTanque() {
        return capacidadeTanque;
    }

    public void setCapacidadeTanque(double capacidadeTanque) {
        this.capacidadeTanque = capacidadeTanque;
    }

    @Override
    public String toString() {
        return "Combustivel{" +
                "tipo='" + tipo + '\'' +
                ", capacidadeTanque=" + capacidadeTanque +
                '}';
    }
}