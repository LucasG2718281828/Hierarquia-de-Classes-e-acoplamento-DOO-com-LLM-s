package br.edu.ifsudestemg.doo.gemini.Funcionario;

public abstract class Funcionario {
    protected Long id;
    protected String nome;
    protected Double salario;
    protected Departamento departamento;
    protected Equipe equipe;
    protected BonusPolicy bonusPolicy;
    protected Auditoria auditoria;
    protected FolhaPagamento folhaPagamento;

    public Funcionario(Long id, String nome, Double salario,
                       Departamento departamento, Equipe equipe,
                       BonusPolicy bonusPolicy, Auditoria auditoria,
                       FolhaPagamento folhaPagamento) {
        this.id = id;
        this.nome = nome;
        this.salario = salario;
        this.departamento = departamento;
        this.equipe = equipe;
        this.bonusPolicy = bonusPolicy;
        this.auditoria = auditoria;
        this.folhaPagamento = folhaPagamento;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getSalario() {
        return salario;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public BonusPolicy getBonusPolicy() {
        return bonusPolicy;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public FolhaPagamento getFolhaPagamento() {
        return folhaPagamento;
    }

    /**
     * Calcula o bônus salarial de acordo com a política definida.
     */
    public double calcularBonus() {
        if (bonusPolicy == null) {
            return 0.0;
        }
        return bonusPolicy.aplicarBonus(this);
    }

    /**
     * Valida se a equipe subordinada está de acordo com as regras do gestor.
     * Por padrão, apenas verifica se a equipe não é nula.
     */
    public boolean validarEquipe() {
        if (equipe == null) {
            return false;
        }
        return equipe.validarMembros();
    }

    // Outros comportamentos comuns podem ser adicionados aqui.
}