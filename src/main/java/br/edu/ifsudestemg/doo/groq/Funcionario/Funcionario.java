package br.edu.ifsudestemg.doo.groq.Funcionario;

public class Funcionario {
    private String nome;
    private double salarioBase;
    private Departamento departamento;
    private FolhaPagamento folhaPagamento;
    private BonusPolicy bonusPolicy;
    private Equipe equipe;
    private Auditoria auditoria;

    public Funcionario(String nome, double salarioBase, Departamento departamento,
                       FolhaPagamento folhaPagamento, BonusPolicy bonusPolicy,
                       Equipe equipe, Auditoria auditoria) {
        this.nome = nome;
        this.salarioBase = salarioBase;
        this.departamento = departamento;
        this.folhaPagamento = folhaPagamento;
        this.bonusPolicy = bonusPolicy;
        this.equipe = equipe;
        this.auditoria = auditoria;
    }

    public double calcularBonus() {
        double bonus = bonusPolicy.calculateBonus(this);
        auditoria.log("Calcular bônus para " + nome + ": " + bonus);
        return bonus;
    }

    public void validarEquipeSubordinada() {
        equipe.validate(this);
        auditoria.log("Validar equipe subordinada para " + nome);
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public FolhaPagamento getFolhaPagamento() {
        return folhaPagamento;
    }

    public void setFolhaPagamento(FolhaPagamento folhaPagamento) {
        this.folhaPagamento = folhaPagamento;
    }

    public BonusPolicy getBonusPolicy() {
        return bonusPolicy;
    }

    public void setBonusPolicy(BonusPolicy bonusPolicy) {
        this.bonusPolicy = bonusPolicy;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }
}