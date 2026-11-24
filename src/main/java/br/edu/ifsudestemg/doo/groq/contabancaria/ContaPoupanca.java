package br.edu.ifsudestemg.doo.groq.contabancaria;

public class ContaPoupanca extends ContaBancaria {

    public ContaPoupanca(String numeroConta, double saldo, Titular titular, Moeda moeda) {
        super(numeroConta, saldo, titular, moeda);
    }

    @Override
    public void sacar(double valor) throws Exception {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo.");
        }
        double tarifa = valor * 0.005; // 0.5% de tarifa
        double total = valor + tarifa;
        if (saldo >= total) {
            saldo -= total;
            registrarExtrato("Saque Poupança", valor, saldo);
        } else {
            throw new Exception("Saldo insuficiente para saque com tarifa.");
        }
    }
}