**Java – Hierarquia de Personagens de Jogo**

```java
// ---------------------------------------------------------------
// 1.  Dependências (stubs – apenas a estrutura necessária)
// ---------------------------------------------------------------

/**
 * Representa uma arma que pode ser usada por um personagem.
 */
public class Arma {
    private String nome;
    private int danoBase;

    public Arma(String nome, int danoBase) {
        this.nome = nome;
        this.danoBase = danoBase;
    }

    public int getDanoBase() {
        return danoBase;
    }

    public String getNome() {
        return nome;
    }
}

/**
 * Representa uma armadura que pode ser usada por um personagem.
 */
public class Armadura {
    private String nome;
    private int defesaBase;

    public Armadura(String nome, int defesaBase) {
        this.nome = nome;
        this.defesaBase = defesaBase;
    }

    public int getDefesaBase() {
        return defesaBase;
    }

    public String getNome() {
        return nome;
    }
}

/**
 * Representa um efeito de status (ex.: “Envenenado”, “Curado”, “Bênção”).
 */
public class EfeitoStatus {
    private String nome;
    private int duracao;   // em turnos

    public EfeitoStatus(String nome, int duracao) {
        this.nome = nome;
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracao() {
        return duracao;
    }

    public void reduzirDuracao() {
        if (duracao > 0) duracao--;
    }
}

/**
 * Inventário simples que armazena armas, armaduras e outros itens.
 */
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Arma> armas = new ArrayList<>();
    private List<Armadura> armaduras = new ArrayList<>();

    public void adicionarArma(Arma arma) {
        armas.add(arma);
    }

    public void adicionarArmadura(Armadura armadura) {
        armaduras.add(armadura);
    }

    public List<Arma> getArmas() {
        return armas;
    }

    public List<Armadura> getArmaduras() {
        return armaduras;
    }
}
```

```java
// ---------------------------------------------------------------
// 2.  Classe Pai – PersonagemJogo
// ---------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que representa qualquer personagem do jogo.
 * Contém a lógica básica de combate e cálculo de dano.
 */
public abstract class PersonagemJogo {

    /* ---------- Atributos ---------- */
    protected String nome;
    protected int vida;          // pontos de vida
    protected int ataque;        // força de ataque base
    protected int defesa;        // força de defesa base
    protected Arma arma;         // arma equipada
    protected Armadura armadura; // armadura equipada
    protected Inventario inventario;
    protected List<EfeitoStatus> efeitos = new ArrayList<>();

    /* ---------- Construtor ---------- */
    public PersonagemJogo(String nome, int vida, int ataque, int defesa) {
        this.nome = nome;
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.inventario = new Inventario();
    }

    /* ---------- Getters / Setters ---------- */
    public String getNome() { return nome; }
    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }
    public int getAtaque() { return ataque; }
    public int getDefesa() { return defesa; }
    public Arma getArma() { return arma; }
    public Armadura getArmadura() { return armadura; }
    public Inventario getInventario() { return inventario; }
    public List<EfeitoStatus> getEfeitos() { return efeitos; }

    public void equiparArma(Arma arma) { this.arma = arma; }
    public void equiparArmadura(Armadura armadura) { this.armadura = armadura; }

    /* ---------- Lógica de Combate ---------- */

    /**
     * Processa um turno de combate contra outro personagem.
     * @param adversario O personagem que está atacando.
     */
    public void processarCombate(PersonagemJogo adversario) {
        // 1. Aplicar efeitos de status antes do ataque
        aplicarEfeitos();

        // 2. Calcular dano que este personagem causa ao adversário
        int dano = calcularDano();

        // 3. Adicionar dano ao adversário
        adversario.receberDano(dano);

        // 4. Reduzir duração dos efeitos e remover expirados
        atualizarEfeitos();
    }

    /**
     * Calcula o dano que este personagem causa em um ataque.
     * Considera arma, ataque base e efeitos de status.
     */
    public int calcularDano() {
        int dano = ataque;

        // + dano da arma, se houver
        if (arma != null) {
            dano += arma.getDanoBase();
        }

        // Efeitos de status que aumentam dano (exemplo simples)
        for (EfeitoStatus e : efeitos) {
            if ("Bênção".equals(e.getNome())) {
                dano += 5; // bônus fixo
            }
        }

        return Math.max(dano, 0); // nunca negativo
    }

    /**
     * Recebe dano, subtraindo a defesa e armadura.
     */
    public void receberDano(int dano) {
        int defesaTotal = defesa;
        if (armadura != null) {
            defesaTotal += armadura.getDefesaBase();
        }

        int danoFinal = dano - defesaTotal;
        if (danoFinal < 0) danoFinal = 0;

        vida -= danoFinal;
        if (vida < 0) vida = 0;
    }

    /* ---------- Efeitos de Status ---------- */

    /**
     * Aplica efeitos de status que alteram atributos temporariamente.
     * (Exemplo: “Envenenado” causa dano a cada turno)
     */
    protected void aplicarEfeitos() {
        for (EfeitoStatus e : efeitos) {
            if ("Envenenado".equals(e.getNome())) {
                vida -= 2; // dano por turno
                if (vida < 0) vida = 0;
            }
        }
    }

    /**
     * Atualiza a duração dos efeitos e remove os que expiraram.
     */
    protected void atualizarEfeitos() {
        List<EfeitoStatus> remover = new ArrayList<>();
        for (EfeitoStatus e : efeitos) {
            e.reduzirDuracao();
            if (e.getDuracao() <= 0) remover.add(e);
        }
        efeitos.removeAll(remover);
    }

    /* ---------- Representação ---------- */

    @Override
    public String toString() {
        return String.format("%s [Vida=%d, Ataque=%d, Defesa=%d, Arma=%s, Armadura=%s]",
                nome, vida, ataque, defesa,
                (arma != null ? arma.getNome() : "Nenhuma"),
                (armadura != null ? armadura.getNome() : "Nenhuma"));
    }
}
```

```java
// ---------------------------------------------------------------
// 3.  Classe Filho – Guerreiro
// ---------------------------------------------------------------

/**
 * Guerreiro – personagem focado em combate corpo a corpo.
 */
public class Guerreiro extends PersonagemJogo {

    public Guerreiro(String nome, int vida, int ataque, int defesa) {
        super(nome, vida, ataque, defesa);
    }

    /**
     * Guerreiro tem um bônus de ataque quando usa arma de duas mãos.
     */
    @Override
    public int calcularDano() {
        int dano = super.calcularDano();

        // Exemplo: se a arma tem dano base > 15, aplica bônus
        if (arma != null && arma.getDanoBase() >