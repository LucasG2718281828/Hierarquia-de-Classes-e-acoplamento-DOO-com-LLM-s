package br.edu.ifsudestemg.doo.mistral.veiculo;

public class Carro extends Veiculo {
    private int numeroDePortas;
    private int capacidadePassageiros;

    public Carro(String chassi, Motor motor, Combustivel combustivel, Placa placa, int numeroDePortas, int capacidadePassageiros) {
        super(chassi, motor, combustivel, placa);
        this.numeroDePortas = numeroDePortas;
        this.capacidadePassageiros = capacidadePassageiros;
    }

    public int getNumeroDePortas() {
        return numeroDePortas;
    }

    public void setNumeroDePortas(int numeroDePortas) {
        this.numeroDePortas = numeroDePortas;
    }

    public int getCapacidadePassageiros() {
        return capacidadePassageiros;
    }

    public void setCapacidadePassageiros(int capacidadePassageiros) {
        this.capacidadePassageiros = capacidadePassageiros;
    }

    @Override
    public double calcularConsumo() {
        // Lógica específica para carro
        return 12.5;
    }

    @Override
    public double acelerar() {
        // Lógica específica para carro
        return 8.5;
    }

    @Override
    public String toString() {
        return "Carro{" +
                "numeroDePortas=" + numeroDePortas +
                ", capacidadePassageiros=" + capacidadePassageiros +
                "} " + super.toString();
    }
}