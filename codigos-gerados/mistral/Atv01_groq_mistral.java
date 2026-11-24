// Java – Hierarquia principal (Animal, Cachorro, Gato)  

package mistral;

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
    private Som som;          // composição – responsabilidade de reproduzir o som
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
     * Método abstrato que descreve como o animal se movimenta.
     * Cada sub‑classe deve fornecer sua própria implementação.
     */
    public abstract void movimentar();

    /* ---------- Representação textual ---------- */
    @Override
    public String toString() {
        return String.format("%s (Idade: %d)", nome, idade);
    }
}
```

```java
// ──────────────────────────────────────────────────────────────────────
// 2.  Classe Cachorro
// ──────────────────────────────────────────────────────────────────────
package br.com.exemplo.animais;

import br.com.exemplo.dependencias.Som;
import br.com.exemplo.dependencias.Alimento;

/**
 * Representa um cachorro.  
 * O cachorro late e corre.
 */
public class Cachorro extends Animal {

    /* ---------- Construtor ---------- */
    public Cachorro(String nome, int idade, Som som, Alimento alimento) {
        super(nome, idade, som, alimento);
    }

    /* ---------- Implementação de movimentar ---------- */
    @Override
    public void movimentar() {
        System.out.println(getNome() + " está correndo alegremente!");
    }

    /* ---------- Método adicional (opcional) ---------- */
    public void abanarRabo() {
        System.out.println(getNome() + " está abanando o rabo.");
    }
}
```

```java
// ──────────────────────────────────────────────────────────────────────
// 3.  Classe Gato
// ──────────────────────────────────────────────────────────────────────
package br.com.exemplo.animais;

import br.com.exemplo.dependencias.Som;
import br.com.exemplo.dependencias.Alimento;

/**
 * Representa um gato.  
 * O gato mia e se move de forma elegante.
 */
public class Gato extends Animal {

    /* ---------- Construtor ---------- */
    public Gato(String nome, int idade, Som som, Alimento alimento) {
        super(nome, idade, som, alimento);
    }

    /* ---------- Implementação de movimentar ---------- */
    @Override
    public void movimentar() {
        System.out.println(getNome() + " está se movendo com elegância.");
    }

    /* ---------- Método adicional (opcional) ---------- */
    public void arranhar() {
        System.out.println(getNome() + " está arranhando a caixa de areia.");
    }
}
```

---

**Java – Dependências (Som e Alimento)**  
```java
// ──────────────────────────────────────────────────────────────────────
// 4.  Classe Som (stub)
// ──────────────────────────────────────────────────────────────────────
package br.com.exemplo.dependencias;

/**
 * Stub da classe responsável por reproduzir sons.
 * Apenas a estrutura necessária para compilar.
 */
public class Som {

    /* ---------- Construtor ---------- */
    public Som() {
        // Construtor vazio
    }

    /* ---------- Método de reprodução ---------- */
    public void reproduzir() {
        // Implementação vazia – apenas para compilar
    }
}
```

```java
// ──────────────────────────────────────────────────────────────────────
// 5.  Classe Alimento (stub)
// ──────────────────────────────────────────────────────────────────────
package br.com.exemplo.dependencias;

/**
 * Stub da classe responsável por servir alimento.
 * Apenas a estrutura necessária para compilar.
 */
public class Alimento {

    /* ---------- Construtor ---------- */
    public Alimento() {
        // Construtor vazio
    }

    /* ---------- Método de servir alimento ---------- */
    public void servir() {
        // Implementação vazia – apenas para compilar
    }
}
```

---

### Como usar

```java
import br.com.exemplo.animais.*;
import br.com.exemplo.dependencias.*;

public class Main {
    public static void main(String[] args) {
        Som somCachorro = new Som();
        Alimento alimentoCachorro = new Alimento();

        Cachorro cachorro = new Cachorro("Rex", 4, somCachorro, alimentoCachorro);
        cachorro.emitirSom();      // chama Som.reproduzir()
        cachorro.movimentar();     // imprime mensagem de corrida
        cachorro.abanarRabo();     // método adicional

        Som somGato = new Som();
        Alimento alimentoGato = new Alimento();

        Gato gato = new Gato("Mia", 2, somGato, alimentoGato);
        gato.emitirSom();          // chama Som.reproduzir()
        gato.movimentar();         // imprime mensagem de elegância
        gato.arranhar();           // método adicional
    }
}
```

> **Observação**:  
> - As classes `Som` e `Alimento` são apenas *stubs* (esqueleto) para que o código compile.  
> - Se quiser adicionar lógica real, basta implementar os métodos `reproduzir()` e `servir()`.  
> - A estrutura de pacotes (`br.com.exemplo.animais` e `br.com.exemplo.dependencias`) pode ser ajustada conforme a convenção da sua aplicação.