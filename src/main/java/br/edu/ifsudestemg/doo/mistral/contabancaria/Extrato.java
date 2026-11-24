package br.edu.ifsudestemg.doo.mistral.contabancaria;

import java.util.ArrayList;
import java.util.List;

public class Extrato {
    private List<String> movimentos;

    public Extrato() {
        this.movimentos = new ArrayList<>();
    }

    public void registrarMovimento(String descricao) {
        movimentos.add(descricao);
    }

    public List<String> getMovimentos() {
        return movimentos;
    }
}