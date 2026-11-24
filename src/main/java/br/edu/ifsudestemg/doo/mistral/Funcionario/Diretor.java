package br.edu.ifsudestemg.doo.mistral.Funcionario;

public class Diretor extends Funcionario {

    public Diretor(String nome, double salarioBase, Departamento departamento,
                   FolhaPagamento folhaPagamento, BonusPolicy bonusPolicy,
                   Equipe equipe, Auditoria auditoria) {
        super(nome, salarioBase, departamento, folhaPagamento, bonusPolicy, equipe, auditoria);
    }

    @Override
    public double calcularBonusSalarial() {
        double bonus = super.calcularBonusSalarial();
        // Diretor recebe um adicional de 20% sobre o bônus calculado
        return bonus * 1.20;
    }

    @Override
    public boolean validarEquipeSubordinada() {
        boolean valido = super.validarEquipeSubordinada();
        // Validação adicional para Diretor
        return valido;
    }
}