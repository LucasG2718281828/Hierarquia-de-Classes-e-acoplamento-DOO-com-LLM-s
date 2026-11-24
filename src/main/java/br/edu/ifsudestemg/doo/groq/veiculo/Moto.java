package br.edu.ifsudestemg.doo.groq.veiculo;

public class Moto extends Veiculo {

    public Moto(String chassi, Motor motor, Combustivel combustivel, Placa placa) {
        super(chassi, motor, combustivel, placa);
    }

    @Override
    public void acelerar() {
        super.acelerar();
        System.out.println("Moto está acelerando em 2 rodas.");
    }
}