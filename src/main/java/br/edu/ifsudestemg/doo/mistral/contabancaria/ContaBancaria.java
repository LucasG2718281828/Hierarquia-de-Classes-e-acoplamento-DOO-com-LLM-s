package br.edu.ifsudestemg.doo.mistral.contabancaria;

public abstract class ContaBancaria {
    private int numero;
    private double saldo;
    private Titular titular;
    private Extrato extrato;
    private Moeda moeda;

    public ContaBancaria(int numero, Titular titular, Moeda moeda) {
        this.numero = numero;
        this.titular = titular;
        this.moeda = moeda;
        this.extrato = new Extrato();
        this.saldo = 0.0;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            extrato.registrarMovimento("Depósito de " + moeda.getSimbolo() + valor);
        }
    }

    public abstract void sacar(double valor);

    public double getSaldo() {
        return saldo;
    }

    public int getNumero() {
        return numero;
    }

    public Titular getTitular() {
        return titular;
    }

    public Extrato getExtrato() {
        return extrato;
    }

    public Moeda getMoeda() {
        return moeda;
    }
}