package br.edu.ifsudestemg.doo.mistral.Funcionario;

public abstract class Funcionario {
    protected String nome;
    protected double salarioBase;
    protected Departamento departamento;
    protected FolhaPagamento folhaPagamento;
    protected BonusPolicy bonusPolicy;
    protected Equipe equipe;
    protected Auditoria auditoria;

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

    public double calcularBonusSalarial() {
        return bonusPolicy.calcularBonus(this);
    }

    public boolean validarEquipeSubordinada() {
        return equipe.validar(this);
    }

    // Getters and Setters
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