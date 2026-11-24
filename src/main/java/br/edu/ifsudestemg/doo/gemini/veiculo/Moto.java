package br.edu.ifsudestemg.doo.gemini.veiculo;

public class Moto extends Veiculo {
    private boolean temCarenagem;

    public Moto(String chassi, Motor motor, Combustivel combustivel, Placa placa, boolean temCarenagem) {
        super(chassi, motor, combustivel, placa);
        this.temCarenagem = temCarenagem;
    }

    public boolean isTemCarenagem() {
        return temCarenagem;
    }

    public void setTemCarenagem(boolean temCarenagem) {
        this.temCarenagem = temCarenagem;
    }

    // Comportamento específico: empinar
    public void empinar() {
        System.out.println("A moto está empinando!");
    }

    // Sobrescreve cálculo de consumo para considerar menor peso da moto
    @Override
    public double calcularConsumo(double distanciaKm) {
        double consumoBase = super.calcularConsumo(distanciaKm);
        // Fator de ajuste para moto (ex.: 0.9)
        return consumoBase * 0.9;
    }
}