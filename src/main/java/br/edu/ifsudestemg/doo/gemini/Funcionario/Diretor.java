package br.edu.ifsudestemg.doo.gemini.Funcionario;

public class Diretor extends Funcionario {

    public Diretor(Long id, String nome, Double salario,
                   Departamento departamento, Equipe equipe,
                   BonusPolicy bonusPolicy, Auditoria auditoria,
                   FolhaPagamento folhaPagamento) {
        super(id, nome, salario, departamento, equipe, bonusPolicy, auditoria, folhaPagamento);
    }

    @Override
    public double calcularBonus() {
        // Exemplo: Diretor recebe 30% do salário como bônus, ajustado pela política.
        double base = salario * 0.30;
        if (bonusPolicy != null) {
            base = bonusPolicy.aplicarBonus(this, base);
        }
        return base;
    }

    @Override
    public boolean validarEquipe() {
        // Diretor pode ter equipes de qualquer tamanho, mas todos os membros devem estar ativos.
        if (equipe == null) {
            return false;
        }
        return equipe.todosMembrosAtivos();
    }
}