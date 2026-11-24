**PersonagemJogo.java**

```java
import java.util.ArrayList;
import java.util.List;

/**
 * Classe base que representa um personagem do jogo.
 * Contém a lógica de combate e cálculo de dano com equipamentos.
 */
public class PersonagemJogo {

    /* ---------- Atributos ---------- */
    protected String nome;
    protected int vida;
    protected int ataqueBase;
    protected int defesaBase;

    protected Arma arma;
    protected Armadura armadura;
    protected Inventario inventario;
    protected List<EfeitoStatus> efeitos;

    /* ---------- Construtor ---------- */
    public PersonagemJogo(String nome, int vida, int ataqueBase, int defesaBase) {
        this.nome = nome;
        this.vida = vida;
        this.ataqueBase = ataqueBase;
        this.defesaBase = defesaBase;
        this.inventario = new Inventario();
        this.efeitos = new ArrayList<>();
    }

    /* ---------- Métodos de Equipamento ---------- */
    public void equiparArma(Arma arma) {
        this.arma = arma;
    }

    public void equiparArmadura(Armadura armadura) {
        this.armadura = armadura;
    }

    /* ---------- Métodos de Inventário ---------- */
    public void adicionarItem(Object item) {
        inventario.adicionarItem(item);
    }

    public void removerItem(Object item) {
        inventario.removerItem(item);
    }

    public Inventario getInventario() {
        return inventario;
    }

    /* ---------- Métodos de Status ---------- */
    public void adicionarEfeito(EfeitoStatus efeito) {
        efeitos.add(efeito);
    }

    public void atualizarEfeitos() {
        List<EfeitoStatus> remover = new ArrayList<>();
        for (EfeitoStatus e : efeitos) {
            e.setDuracao(e.getDuracao() - 1);
            if (e.getDuracao() <= 0) {
                remover.add(e);
            }
        }
        efeitos.removeAll(remover);
    }

    /* ---------- Métodos de Combate ---------- */
    /**
     * Processa um ataque contra o adversário.
     *
     * @param adversario O personagem que será atacado.
     */
    public void processarCombate(PersonagemJogo adversario) {
        int dano = calcularDano(adversario);
        adversario.receberDano(dano);
    }

    /**
     * Calcula o dano que este personagem causa ao adversário.
     *
     * @param adversario O alvo do ataque.
     * @return O valor de dano