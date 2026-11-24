package br.edu.ifsudestemg.doo.gemini.contabancaria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Extrato {
    private List<String> movimentos;

    public Extrato() {
        this.movimentos = new ArrayList<>();
    }

    public void adicionarMovimento(String descricao) {
        movimentos.add(descricao);
    }

    public List<String> getMovimentos() {
        return Collections.unmodifiableList(movimentos);
    }

    // Métodos auxiliares como imprimir ou exportar podem ser adicionados.
}