package br.edu.ifsudestemg.doo.gemini.Funcionario;

import java.util.List;

public class Equipe {
    private List<Funcionario> membros;

    public Equipe(List<Funcionario> membros) {
        this.membros = membros;
    }

    public List<Funcionario> getMembros() {
        return membros;
    }

    /**
     * Validação genérica dos membros da equipe.
     */
    public boolean validarMembros() {
        // Stub: sempre retorna true.
        return true;
    }

    /**
     * Verifica se todos os membros estão ativos.
     */
    public boolean todosMembrosAtivos() {
        // Stub: sempre retorna true.
        return true;
    }
}