package br.edu.ifsudestemg.doo.groq.Funcionario;

public class Diretor extends Funcionario {

    private double participacaoLucro;

    public Diretor(String nome, double salarioBase, Departamento departamento,
                   FolhaPagamento folhaPagamento, BonusPolicy bonusPolicy,
                   Equipe equipe, Auditoria auditoria, double participacaoLucro) {
        super(nome, salarioBase, departamento, folhaPagamento, bonusPolicy, equipe, auditoria);
        this.participacaoLucro = participacaoLucro;
    }

    @Override
    public double calcularBonus() {
        double bonusBase = super.calcularBonus();
        double bonusParticipacao = participacaoLucro;
        double totalBonus = bonusBase + bonusParticipacao;
        getAuditoria().log("Diretor " + getNome() + " bônus de participação: " + bonusParticipacao);
        return totalBonus;
    }

    public double getParticipacaoLucro() {
        return participacaoLucro;
    }

    public void setParticipacaoLucro(double participacaoLucro) {
        this.participacaoLucro = participacaoLucro;
    }
}