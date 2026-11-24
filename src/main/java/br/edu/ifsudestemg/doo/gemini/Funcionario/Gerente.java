package br.edu.ifsudestemg.doo.gemini.Funcionario;

public class Gerente extends Funcionario {

    public Gerente(Long id, String nome, Double salario,
                   Departamento departamento, Equipe equipe,
                   BonusPolicy bonusPolicy, Auditoria auditoria,
                   FolhaPagamento folhaPagamento) {
        super(id, nome, salario, departamento, equipe, bonusPolicy, auditoria, folhaPagamento);
    }

    @Override
    public double calcularBonus() {
        // Exemplo: Gerente recebe 20% do salário como bônus, ajustado pela política.
        double base = salario * 0.20;
        if (bonusPolicy != null) {
            base = bonusPolicy.aplicarBonus(this, base);
        }
        return base;
    }

    @Override
    public boolean validarEquipe() {
        // Gerente deve ter no máximo 10 membros na equipe.
        if (equipe == null) {
            return false;
        }
        return equipe.getMembros().size() <= 10 && equipe.validarMembros();
    }
}