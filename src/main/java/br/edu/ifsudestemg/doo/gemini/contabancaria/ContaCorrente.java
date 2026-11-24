package br.edu.ifsudestemg.doo.gemini.contabancaria;

public class ContaCorrente extends ContaBancaria {
    private static final double TARIFA_FIXA = 5.00; // tarifa fixa em unidades da moeda

    public ContaCorrente(String numero, Titular titular, Moeda moeda) {
        super(numero, titular, moeda);
    }

    @Override
    protected double calcularTarifa(double valor) {
        return TARIFA_FIXA;
    }

    // Caso a conta corrente possua funcionalidades adicionais, podem ser adicionadas aqui.
}