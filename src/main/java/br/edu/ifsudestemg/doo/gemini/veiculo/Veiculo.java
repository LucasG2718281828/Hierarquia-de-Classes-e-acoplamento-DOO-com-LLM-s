package br.edu.ifsudestemg.doo.gemini.veiculo;

public abstract class Veiculo {
    protected String chassi;
    protected Motor motor;
    protected Combustivel combustivel;
    protected Placa placa;

    public Veiculo(String chassi, Motor motor, Combustivel combustivel, Placa placa) {
        this.chassi = chassi;
        this.motor = motor;
        this.combustivel = combustivel;
        this.placa = placa;
    }

    // Calcula o consumo de combustível em litros para a distância informada (km)
    public double calcularConsumo(double distanciaKm) {
        // Implementação padrão (pode ser sobrescrita nas subclasses)
        // Exemplo simplificado: consumo = distancia / (potencia do motor * fator)
        if (motor == null) {
            return 0.0;
        }
        double eficiencia = motor.getPotencia() * 0.1; // fator fictício
        if (eficiencia == 0) {
            return 0.0;
        }
        return distanciaKm / eficiencia;
    }

    // Simula a aceleração do veículo em rotações por minuto (RPM)
    public void acelerar(int rpm) {
        // Lógica simplificada: apenas delega ao motor
        if (motor != null) {
            motor.acelerar(rpm);
        }
    }

    // Getters e Setters
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
}