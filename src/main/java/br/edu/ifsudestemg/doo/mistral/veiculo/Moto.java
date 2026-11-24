package br.edu.ifsudestemg.doo.mistral.veiculo;

public class Moto extends Veiculo {
    private boolean temBagageiro;
    private double cilindrada;

    public Moto(String chassi, Motor motor, Combustivel combustivel, Placa placa, boolean temBagageiro, double cilindrada) {
        super(chassi, motor, combustivel, placa);
        this.temBagageiro = temBagageiro;
        this.cilindrada = cilindrada;
    }

    public boolean isTemBagageiro() {
        return temBagageiro;
    }

    public void setTemBagageiro(boolean temBagageiro) {
        this.temBagageiro = temBagageiro;
    }

    public double getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(double cilindrada) {
        this.cilindrada = cilindrada;
    }

    @Override
    public double calcularConsumo() {
        // Lógica específica para moto
        return 25.0;
    }

    @Override
    public double acelerar() {
        // Lógica específica para moto
        return 4.2;
    }

    @Override
    public String toString() {
        return "Moto{" +
                "temBagageiro=" + temBagageiro +
                ", cilindrada=" + cilindrada +
                "} " + super.toString();
    }
}