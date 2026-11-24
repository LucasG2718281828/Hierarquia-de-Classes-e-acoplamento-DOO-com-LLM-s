package br.edu.ifsudestemg.doo.groq.contabancaria;

public abstract class ContaBancaria {
    protected String numeroConta;
    protected double saldo;
    protected Titular titular;
    protected Extrato extrato;
    protected Moeda moeda;

    public ContaBancaria(String numeroConta, double saldo, Titular titular, Moeda moeda) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.titular = titular;
        this.moeda = moeda;
        this.extrato = new Extrato();
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo.");
        }
        saldo += valor;
        registrarExtrato("Depósito", valor, saldo);
    }

    public abstract void sacar(double valor) throws Exception;

    public double getSaldo() {
        return saldo;
    }

    protected void registrarExtrato(String operacao, double valor, double saldoFinal) {
        extrato.registrar(operacao, valor, saldoFinal);
    }
}