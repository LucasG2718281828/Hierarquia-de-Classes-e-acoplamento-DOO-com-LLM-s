**Java – Hierarquia de funcionários**

```java
// ---------------------------------------------------------------------------
// 1. Classe base – Funcionario
// ---------------------------------------------------------------------------

public class Funcionario {

    /* ---------- Atributos ---------- */
    protected int id;
    protected String nome;
    protected double salarioBase;

    /* Dependências */
    protected Departamento departamento;
    protected Equipe equipe;                 // equipe subordinada (se houver)
    protected FolhaPagamento folhaPagamento;
    protected BonusPolicy bonusPolicy;
    protected Auditoria auditoria;

    /* ---------- Construtor ---------- */
    public Funcionario(int id, String nome, double salarioBase,
                       Departamento departamento,
                       FolhaPagamento folhaPagamento,
                       BonusPolicy bonusPolicy,
                       Auditoria auditoria) {

        this.id = id;
        this.nome = nome;
        this.salarioBase = salarioBase;
        this.departamento = departamento;
        this.folhaPagamento = folhaPagamento;
        this.bonusPolicy = bonusPolicy;
        this.auditoria = auditoria;
    }

    /* ---------- Métodos ---------- */

    /**
     * Calcula o bônus salarial de acordo com a política de bônus.
     */
    public double calcularBonus() {
        return bonusPolicy.calcularBonus(this);
    }

    /**
     * Valida a equipe subordinada do gestor.
     * Para um funcionário comum a equipe pode ser nula.
     */
    public boolean validarEquipeSubordinada() {
        if (equipe == null) {
            return true;   // nenhum subordinado → válido
        }
        return equipe.isValid();
    }

    /* ---------- Getters / Setters ---------- */

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    public Equipe getEquipe() { return equipe; }
    public void setEquipe(Equipe equipe) { this.equipe = equipe; }

    public FolhaPagamento getFolhaPagamento() { return folhaPagamento; }
    public void setFolhaPagamento(FolhaPagamento folhaPagamento) { this.folhaPagamento = folhaPagamento; }

    public BonusPolicy getBonusPolicy() { return bonusPolicy; }
    public void setBonusPolicy(BonusPolicy bonusPolicy) { this.bonusPolicy = bonusPolicy; }

    public Auditoria getAuditoria() { return auditoria; }
    public void setAuditoria(Auditoria auditoria) { this.auditoria = auditoria; }
}
```

```java
// ---------------------------------------------------------------------------
// 2. Subclasse – Gerente
// ---------------------------------------------------------------------------

public class Gerente extends Funcionario {

    /* ---------- Atributos adicionais ---------- */
    private int nivel;   // nível de senioridade (ex.: 1‑5)

    /* ---------- Construtor ---------- */
    public Gerente(int id, String nome, double salarioBase,
                   Departamento departamento,
                   FolhaPagamento folhaPagamento,
                   BonusPolicy bonusPolicy,
                   Auditoria auditoria,
                   int nivel) {

        super(id, nome, salarioBase, departamento, folhaPagamento, bonusPolicy, auditoria);
        this.nivel = nivel;
    }

    /* ---------- Métodos sobrescritos ---------- */

    @Override
    public double calcularBonus() {
        double base = super.calcularBonus();
        // Gerente recebe 10 % adicional sobre o bônus calculado
        return base * 1.10;
    }

    @Override
    public boolean validarEquipeSubordinada() {
        boolean valido = super.validarEquipeSubordinada();

        // Validação extra: equipe não pode ter mais que 10 membros
        if (equipe != null && equipe.getTamanho() > 10) {
            return false;
        }
        return valido;
    }

    /* ---------- Getters / Setters ---------- */

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }
}
```

```java
// ---------------------------------------------------------------------------
// 3. Subclasse – Diretor
// ---------------------------------------------------------------------------

public class Diretor extends Funcionario {

    /* ---------- Atributos adicionais ---------- */
    private String areaResponsavel;   // ex.: "Financeiro", "Operações"

    /* ---------- Construtor ---------- */
    public Diretor(int id, String nome, double salarioBase,
                   Departamento departamento,
                   FolhaPagamento folhaPagamento,
                   BonusPolicy bonusPolicy,
                   Auditoria auditoria,
                   String areaResponsavel) {

        super(id, nome, salarioBase, departamento, folhaPagamento, bonusPolicy, auditoria);
        this.areaResponsavel = areaResponsavel;
    }

    /* ---------- Métodos sobrescritos ---------- */

    @Override
    public double calcular