package br.edu.ifsudestemg.doo.groq.contabancaria;

import java.util.ArrayList;
import java.util.List;

public class Extrato {
    private List<String> registros;

    public Extrato() {
        this.registros = new ArrayList<>();
    }

    public void registrar(String operacao, double valor, double saldoFinal) {
        String registro = String.format("%s: R$ %.2f | Saldo Final: R$ %.2f", operacao, valor, saldoFinal);
        registros.add(registro);
    }

    public List<String> getRegistros() {
        return registros;
    }
}