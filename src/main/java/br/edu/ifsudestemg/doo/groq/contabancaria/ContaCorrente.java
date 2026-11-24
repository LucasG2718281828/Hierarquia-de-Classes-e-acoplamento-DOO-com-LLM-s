package br.edu.ifsudestemg.doo.groq.contabancaria;

public class ContaCorrente extends ContaBancaria {

    public ContaCorrente(String numeroConta, double saldo, Titular titular, Moeda moeda) {
        super(numeroConta, saldo, titular, moeda);
    }

    @Override
    public void sacar(double valor) throws Exception {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo.");
        }
        double tarifa = valor * 0.01; // 1% de tarifa
        double total = valor + tarifa;
        if (saldo >= total) {
            saldo -= total;
            registrarExtrato("Saque Corrente", valor, saldo);
        } else {
            throw new Exception("Saldo insuficiente para saque com tarifa.");
        }
    }
}