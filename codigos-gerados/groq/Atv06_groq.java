**Java – Hierarquia de Funcionários**

```java
/* ------------- 1. Classe Pai – Funcionario ------------- */
package empresa;

import java.util.Objects;

/**
 * Representa um funcionário da empresa.
 * Possui métodos para cálculo de bônus e validação da equipe subordinada.
 */
public class Funcionario {

    /* ---------- Atributos ---------- */
    protected int id;
    protected String nome;
    protected double salarioBase;

    /* Dependências */
    protected Departamento departamento;
    protected Equipe equipe;          // equipe subordinada (para gestores)
    protected FolhaPagamento folhaPagamento;
    protected BonusPolicy bonusPolicy;
    protected Auditoria auditoria;

    /* ---------- Construtor ---------- */
    public Funcionario(int id,
                       String nome,
                       double salarioBase,
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
     * Calcula o bônus salarial do funcionário.
     * Delegado à política de bônus configurada.
     */
    public double calcularBonus() {
        return bonusPolicy.calcularBonus(this);
    }

    /**
     * Valida a equipe subordinada do gestor.
     * Para funcionários comuns, a equipe pode ser nula.
     */
    public boolean validarEquipeSubordinada() {
        if (equipe == null) {
            return true; // não há equipe a validar
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

    /* ---------- equals / hashCode / toString ---------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Funcionario)) return false;
        Funcionario that = (Funcionario) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", salarioBase=" + salarioBase +
                '}';
    }
}
```

---

```java
/* ------------- 2. Classe Filha – Gerente ------------- */
package empresa;

/**
 * Representa um gerente, que possui nível gerencial e regras de bônus/validação
 * específicas.
 */
public class Gerente extends Funcionario {

    /* ---------- Atributos ---------- */
    private int nivelGerencial;   // 1 = nível mais baixo, 5 = nível mais alto

    /* ---------- Construtor ---------- */
    public Gerente(int id,
                   String nome,
                   double salarioBase,
                   Departamento departamento,
                   FolhaPagamento folhaPagamento,
                   BonusPolicy bonusPolicy,
                   Auditoria auditoria,
                   int nivelGerencial) {

        super(id, nome, salarioBase, departamento, folhaPagamento, bonusPolicy, auditoria);
        this.nivelGerencial = nivelGerencial;
    }

    /* ---------- Métodos ---------- */

    @Override
    public double calcularBonus() {
        double base = super.calcularBonus();
        // Gerente recebe 10% adicional sobre o bônus calculado
        return base * 1.10;
    }

    @Override
    public boolean validarEquipeSubordinada() {
        boolean valido = super.validarEquipeSubordinada();
        if (!valido) return false;

        // Exemplo de regra adicional: equipe não pode ter mais que 10 membros
        if (equipe != null && equipe.getTamanho() > 10) {
            return false;
        }
        return true;
    }

    /* ---------- Getters / Setters ---------- */

    public int getNivelGerencial() { return nivelGerencial; }
    public void setNivelGerencial(int nivelGerencial) { this