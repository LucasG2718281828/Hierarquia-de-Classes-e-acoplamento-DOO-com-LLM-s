package br.edu.ifsudestemg.doo.gemini.veiculo;

public class Carro extends Veiculo {
    private int numeroDePortas;

    public Carro(String chassi, Motor motor, Combustivel combustivel, Placa placa, int numeroDePortas) {
        super(chassi, motor, combustivel, placa);
        this.numeroDePortas = numeroDePortas;
    }

    public int getNumeroDePortas() {
        return numeroDePortas;
    }

    public void setNumeroDePortas(int numeroDePortas) {
        this.numeroDePortas = numeroDePortas;
    }

    // Exemplo de comportamento específico: abrir porta
    public void abrirPorta(int porta) {
        if (porta > 0 && porta <= numeroDePortas) {
            System.out.println("Porta " + porta + " aberta.");
        } else {
            System.out.println("Porta inexistente.");
        }
    }

    // Sobrescreve cálculo de consumo para considerar peso adicional de carro
    @Override
    public double calcularConsumo(double distanciaKm) {
        double consumoBase = super.calcularConsumo(distanciaKm);
        // Fator de ajuste para carro (ex.: 1.1)
        return consumoBase * 1.1;
    }
}