package br.edu.ifsudestemg.doo.groq.veiculo;

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

    public double calcularConsumo() {
        if (motor == null || combustivel == null) {
            return 0;
        }
        return motor.getPotencia() / combustivel.getCapacidade();
    }

    public void acelerar() {
        if (motor != null) {
            System.out.println("Acelerando com potência " + motor.getPotencia() + " HP.");
        } else {
            System.out.println("Motor não definido.");
        }
    }
}