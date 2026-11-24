**Java – Hierarquia de contas bancárias**

```java
// ──────────────────────────────────────────────────────────────────────
// 1.  Pacote principal
// ──────────────────────────────────────────────────────────────────────
package com.banco;

// ──────────────────────────────────────────────────────────────────────
// 2.  Classe Pai – ContaBancaria
// ──────────────────────────────────────────────────────────────────────
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Classe abstrata que representa uma conta bancária genérica.
 * Contém a lógica básica de depósito, saque e manutenção de extrato.
 */
public abstract class ContaBancaria {

    /* ------------------------------------------------------------------ */
    /*  Atributos (encapsulados)                                          */
    /* ------------------------------------------------------------------ */
    protected Titular titular;          // Titular da conta
    protected BigDecimal saldo;         // Saldo atual
    protected Extrato extrato;          // Extrato da conta
    protected Moeda moeda;              // Moeda da conta

    /* ------------------------------------------------------------------ */
    /*  Construtor                                                        */
    /* ------------------------------------------------------------------ */
    public ContaBancaria(Titular titular, BigDecimal saldoInicial, Moeda moeda) {
        this.titular = Objects.requireNonNull(titular, "Titular não pode ser nulo");
        this.saldo   = saldoInicial != null ? saldoInicial : BigDecimal.ZERO;
        this.moeda   = Objects.requireNonNull(moeda, "Moeda não pode ser nula");
        this.extrato = new Extrato();          // Extrato vazio
    }

    /* ------------------------------------------------------------------ */
    /*  Métodos públicos                                                   */
    /* ------------------------------------------------------------------ */

    /**
     * Depósito de um valor na conta.
     *
     * @param valor valor a ser depositado
     */
    public void depositar(BigDecimal valor) {
        validarValor(valor);
        saldo = saldo.add(valor);
        extrato.adicionarMovimento("Depósito", valor);
    }

    /**
     * Saque de um valor na conta. A implementação padrão apenas
     * subtrai o valor do saldo e registra no extrato.
     *
     * @param valor valor a ser sacado
     */
    public void sacar(BigDecimal valor) {
        validarValor(valor);
        if (saldo.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        saldo = saldo.subtract(valor);
        extrato.adicionarMovimento("Saque", valor.negate());
    }

    /**
     * Retorna o saldo atual da conta.
     *
     * @return saldo
     */
    public BigDecimal getSaldo() {
        return saldo;
    }

    /**
     * Retorna o titular da conta.
     *
     * @return titular
     */
    public Titular getTitular() {
        return titular;
    }

    /**
     * Retorna o extrato da conta.
     *
     * @return extrato
     */
    public Extrato getExtrato() {
        return extrato;
    }

    /**
     * Retorna a moeda da conta.
     *
     * @return moeda
     */
    public Moeda getMoeda() {
        return moeda;
    }

    /* ------------------------------------------------------------------ */
    /*  Métodos auxiliares (protegidos)                                   */
    /* ------------------------------------------------------------------ */

    /**
     * Calcula a tarifa a ser cobrada sobre um saque.
     * Subclasse pode sobrescrever para aplicar tarifa própria.
     *
     * @param valor valor do saque
     * @return tarifa (pode ser zero)
     */
    protected BigDecimal calcularTarifa(BigDecimal valor) {
        return BigDecimal.ZERO;   // Por padrão, nenhuma tarifa
    }

    /**
     * Valida se o valor informado é positivo.
     *
     * @param valor valor a ser validado
     */
    protected void validarValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido: " + valor);
        }
    }

    /* ------------------------------------------------------------------ */
    /*  Sobrescrita de toString                                           */
    /* ------------------------------------------------------------------ */
    @Override
    public String toString() {
        return String.format(
                "%s | Titular: %s | Saldo: %s %s",
                this.getClass().getSimpleName(),
                titular,
                saldo,
                moeda.getSimbolo()
        );
    }
}

// ──────────────────────────────────────────────────────────────────────
// 3.  Classe Filha – ContaCorrente
// ──────────────────────────────────────────────────────────────────────
import java.math.BigDecimal;

/**
 * Conta corrente que cobra uma tarifa fixa de 1% sobre cada saque.
 */
public class ContaCorrente extends ContaBancaria {

    private static final BigDecimal TARIFA_PERCENTUAL = new BigDecimal("0.01"); // 1%

    public ContaCorrente(Titular titular, BigDecimal saldoInicial, Moeda moeda) {
        super(titular, saldoInicial, moeda);
    }

    @Override
    protected BigDecimal calcularTarifa(BigDecimal valor) {
        return valor.multiply(TARIFA_PERCENTUAL);
    }

    @Override
    public void sacar(BigDecimal valor) {
        validarValor(valor);
        BigDecimal tarifa = calcularTarifa(valor);
        BigDecimal valorComTarifa = valor.add(tarifa);

        if (saldo.compareTo(valorComTarifa) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para saque + tarifa");
        }

        saldo = saldo.subtract(valorComTarifa);
       