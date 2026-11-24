package br.edu.ifsudestemg.doo.gemini.contabancaria;

public class ContaPoupanca extends ContaBancaria {
    private static final double PERCENTUAL_TARIFA = 0.005; // 0,5% do valor sacado

    public ContaPoupanca(String numero, Titular titular, Moeda moeda) {
        super(numero, titular, moeda);
    }

    @Override
    protected double calcularTarifa(double valor) {
        return valor * PERCENTUAL_TARIFA;
    }

    // Funcionalidades específicas da poupança podem ser adicionadas aqui.
}