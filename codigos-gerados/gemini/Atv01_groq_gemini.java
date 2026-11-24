**Java–Hierarquia principal(Animal,Cachorro,Gato)**```java
// ──────────────────────────────────────────────────────────────────────
// 1.  Classe abstrata Animal
// ──────────────────────────────────────────────────────────────────────
package br.com.exemplo.animais;

import br.com.exemplo.dependencias.Alimento;
import br.com.exemplo.dependencias.Som;

/**
 * Classe base para todos os animais do exemplo.
 * <p>
 * Cada animal possui um nome, idade, um som que pode ser reproduzido
 * e um alimento que pode ser servido. Os métodos {@code emitirSom()}
 * e {@code movimentar()} são implementados pelas subclasses.
 */
public abstract class Animal {

    /* ---------- Atributos ---------- */
    private String nome;
    private int idade;
    private Som som; // composição – responsabilidade de reproduzir o som
    private Alimento alimento; // composição – responsabilidade de servir alimento

    /* ---------- Construtor ---------- */
    public Animal(String nome, int idade, Som som, Alimento alimento) {
        this.nome = nome;
        this.idade = idade;
        this.som = som;
        this.alimento = alimento;
    }

    /* ---------- Getters / Setters ---------- */
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Som getSom() {
        return som;
    }

    public void setSom(Som som) {
        this.som = som;
    }

    public Alimento getAlimento() {
        return alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    /* ---------- Métodos de comportamento ---------- */
    /**
     * Reproduz o som característico do animal.
     */
    public void emitirSom() {
        if (som != null) {
            som.reproduzir();
        } else {
            System.out.println(nome + " não tem som definido.");
        }
    }

    /**
     * Movimento genérico – pode ser sobrescrito pelas subclasses.
     */
    public void movimentar() {
        System.out.println(nome + " está se movendo.");
    }

    /* ---------- Representação textual ---------- */
    @Override
    public String toString() {
        return String.format("Animal [nome=%s, idade=%d]", nome, idade);
    }
}```

```java
// ──────────────────────────────────────────────────────────────────────
// 2. Classe Cachorro (filha de Animal)
// ──────────────────────────────────────────────────────────────────────
package br.com.exemplo.animais;

import br.com.exemplo.dependencias.Som;
import br.com.exemplo.dependencias.Alimento;

/**
 * Representa um cachorro.  
 * Sobrescreve os métodos de comportamento para refletir o comportamento típico de um cão.
 */
public class Cachorro extends Animal {

    /* ---------- Construtor ---------- */
    public Cachorro(String nome, int idade, Som som, Alimento alimento) {
        super(nome, idade, som, alimento);
    }

    /* ---------- Sobrescrita de métodos ---------- */
    @Override
    public void emitirSom() {
        System.out.print(getNome() + " diz: ");
        super.emitirSom(); // delega a Som.reproduzir()
    }

    @Override
    public void movimentar() {
        System.out.println(getNome() + " corre alegremente.");
    }
}```

```java
// ──────────────────────────────────────────────────────────────────────
// 3. Classe Gato (filha de Animal)
// ──────────────────────────────────────────────────────────────────────
package br.com.exemplo.animais;

import br.com.exemplo.dependencias.Som;
import br.com.exemplo.dependencias.Alimento;

/**
 * Representa um gato.
 * Sobrescreve os métodos de comportamento para refletir o comportamento típico
 * de um gato.
 */
public class Gato extends Animal {

    /* ---------- Construtor ---------- */
    public Gato(String nome, int idade, Som som, Alimento alimento) {
        super(nome, idade, som, alimento);
    }

    /* ---------- Sobrescrita de métodos ---------- */
    @Override
    public void emitirSom() {
        System.out.print(getNome() + " faz: ");
        super.emitirSom(); // delega a Som.reproduzir()
    }

    @Override
    public void movimentar() {
        System.out.println(getNome() + " se move silenciosamente.");
    }}```

    ---

    **Java–Dependências(Som e Alimento)**```java
    // ──────────────────────────────────────────────────────────────────────
    // 4. Classe Som (stub)
    // ──────────────────────────────────────────────────────────────────────
    package br.com.exemplo.dependencias;

    /**
     * Stub da classe responsável por reproduzir sons.
     * Apenas a estrutura mínima é fornecida para que o código compile.
     */
    public class Som {

        /* ---------- Atributo opcional ---------- */
        private String descricao;

        /* ---------- Construtor ---------- */
        public Som(String descricao) {
            this.descricao = descricao;
        }

        /* ---------- Método de reprodução ---------- */
        public void reproduzir() {
            // Implementação fictícia – apenas exibe a descrição
            System.out.println(descricao);
        }

        /* ---------- Getters / Setters ---------- */
        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
    }```

    ```java
    // ──────────────────────────────────────────────────────────────────────
    // 5. Classe Alimento (stub)
    // ──────────────────────────────────────────────────────────────────────
package br.com.exemplo.dependencias;

    /**
     * Stub da classe responsável por servir alimento.
     * Apenas a estrutura mínima é fornecida para que o código compile.
     */
    public class Alimento {

        /* ---------- Atributo opcional ---------- */
        private String tipo;

        /* ---------- Construtor ---------- */
        public Alimento(String tipo) {
            this.tipo = tipo;
        }

        /* ---------- Método de serviço ---------- */
        public void servir() {
            // Implementação fictícia – apenas exibe o tipo de alimento
            System.out.println("Servindo alimento: " + tipo);
        }

        /* ---------- Getters / Setters ---------- */
        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
}```

---

###

Como usar

```
java
import br.com.exemplo.animais.Cachorro;
import br.com.exemplo.animais.Gato;
import br.com.exemplo.dependencias.Alimento;
import br.com.exemplo.dependencias.Som;

public class Main {
    public static void main(String[] args) {
        Som latido = new Som("Au Au!");
        Som miado = new Som("Miau!");

        Alimento ração = new Alimento("ração");
        Alimento peixe = new Alimento("peixe");

        Cachorro dog = new Cachorro("Rex", 5, latido, ração);
        Gato cat = new Gato("Mia", 3, miado, peixe);

        dog.emitirSom();
        dog.movimentar();
        dog.getAlimento().servir();

        cat.emitirSom();
        cat.movimentar();
        cat.getAlimento().servir();
    }
}```

O código
acima compila
e demonstra
a hierarquia
de classes, a
composição com
as dependências`Som`e`Alimento`,
e a
sobrescrita de
métodos nas subclasses`Cachorro`e`Gato`
.