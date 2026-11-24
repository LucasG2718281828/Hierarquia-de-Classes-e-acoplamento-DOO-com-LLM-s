package br.edu.ifsudestemg.doo.mistral.veiculo;

public abstract class Veiculo {
    private String chassi;
    private Motor motor;
    private Combustivel combustivel;
    private Placa placa;

    public Veiculo(String chassi, Motor motor, Combustivel combustivel, Placa placa) {
        this.chassi = chassi;
        this.motor = motor;
        this.combustivel = combustivel;
        this.placa = placa;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public Combustivel getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(Combustivel combustivel) {
        this.combustivel = combustivel;
    }

    public Placa getPlaca() {
        return placa;
    }

    public void setPlaca(Placa placa) {
        this.placa = placa;
    }

    public abstract double calcularConsumo();

    public abstract double acelerar();

    @Override
    public String toString() {
        return "Veiculo{" +
                "chassi='" + chassi + '\'' +
                ", motor=" + motor +
                ", combustivel=" + combustivel +
                ", placa=" + placa +
                '}';
    }
}