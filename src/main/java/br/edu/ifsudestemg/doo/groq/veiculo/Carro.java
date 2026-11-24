package br.edu.ifsudestemg.doo.groq.veiculo;

public class Carro extends Veiculo {

    public Carro(String chassi, Motor motor, Combustivel combustivel, Placa placa) {
        super(chassi, motor, combustivel, placa);
    }

    @Override
    public void acelerar() {
        super.acelerar();
        System.out.println("Carro está acelerando em 4 rodas.");
    }
}