package br.edu.ifsudestemg.doo.mistral.Funcionario;

public class Gerente extends Funcionario {

    public Gerente(String nome, double salarioBase, Departamento departamento,
                   FolhaPagamento folhaPagamento, BonusPolicy bonusPolicy,
                   Equipe equipe, Auditoria auditoria) {
        super(nome, salarioBase, departamento, folhaPagamento, bonusPolicy, equipe, auditoria);
    }

    @Override
    public double calcularBonusSalarial() {
        double bonus = super.calcularBonusSalarial();
        // Gerente pode receber um adicional de 10% sobre o bônus calculado
        return bonus * 1.10;
    }

    @Override
    public boolean validarEquipeSubordinada() {
        boolean valido = super.validarEquipeSubordinada();
        // Adicional de validação específica para Gerente
        return valido;
    }
}