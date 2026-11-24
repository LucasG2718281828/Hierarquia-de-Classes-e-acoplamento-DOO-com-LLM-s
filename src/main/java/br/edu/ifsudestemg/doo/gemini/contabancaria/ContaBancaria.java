package br.edu.ifsudestemg.doo.gemini.contabancaria;

import java.util.Objects;

public abstract class ContaBancaria {
    protected String numero;
    protected Titular titular;
    protected Moeda moeda;
    protected double saldo;
    protected Extrato extrato;

    public ContaBancaria(String numero, Titular titular, Moeda moeda) {
        this.numero = Objects.requireNonNull(numero);
        this.titular = Objects.requireNonNull(titular);
        this.moeda = Objects.requireNonNull(moeda);
        this.saldo = 0.0;
        this.extrato = new Extrato();
        registrarMovimento("Abertura da conta");
    }

    public String getNumero() {
        return numero;
    }

    public Titular getTitular() {
        return titular;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public double getSaldo() {
        return saldo;
    }

    public Extrato getExtrato() {
        return extrato;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo.");
        }
        saldo += valor;
        registrarMovimento("Depósito: " + moeda.formatar(valor));
    }

    /**
     * Realiza o saque aplicando a tarifa específica da conta.
     *
     * @param valor valor a ser sacado
     */
    public void sacar(double valor) {
        double tarifa = calcularTarifa(valor);
        double total = valor + tarifa;
        if (total > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente para saque e tarifa.");
        }
        saldo -= total;
        registrarMovimento("Saque: " + moeda.formatar(valor) + " (Tarifa: " + moeda.formatar(tarifa) + ")");
    }

    /**
     * Cada tipo de conta define sua própria regra de cálculo de tarifa.
     *
     * @param valor valor do saque
     * @return valor da tarifa
     */
    protected abstract double calcularTarifa(double valor);

    protected void registrarMovimento(String descricao) {
        extrato.adicionarMovimento(descricao);
    }
}