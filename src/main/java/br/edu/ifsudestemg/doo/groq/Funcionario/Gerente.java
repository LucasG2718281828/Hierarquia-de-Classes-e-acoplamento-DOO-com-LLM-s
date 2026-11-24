package br.edu.ifsudestemg.doo.groq.Funcionario;

public class Gerente extends Funcionario {

    public Gerente(String nome, double salarioBase, Departamento departamento,
                   FolhaPagamento folhaPagamento, BonusPolicy bonusPolicy,
                   Equipe equipe, Auditoria auditoria) {
        super(nome, salarioBase, departamento, folhaPagamento, bonusPolicy, equipe, auditoria);
    }

    @Override
    public double calcularBonus() {
        double bonusBase = super.calcularBonus();
        double bonusGerencial = getSalarioBase() * 0.10; // 10% adicional
        double totalBonus = bonusBase + bonusGerencial;
        getAuditoria().log("Gerente " + getNome() + " bônus gerencial adicional: " + bonusGerencial);
        return totalBonus;
    }
}