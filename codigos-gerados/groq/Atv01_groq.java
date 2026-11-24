**Java – Hierarquia de Animais com dependências “Som” e “Alimento”**

```java
// ------------------------------------------------------------
// 1.  Dependências (stubs – apenas a estrutura necessária)
// ------------------------------------------------------------
package br.com.exemplo.animais;

public class Som {
    /** Retorna o som que o animal faz. */
    public String reproduzir() {
        return "";          // stub – implementação futura
    }
}

public class Alimento {
    /** Nome do alimento. */
    private String nome;

    public Alimento(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
```

```java
// ------------------------------------------------------------
// 2.  Classe Pai – Animal (abstrata)
// ------------------------------------------------------------
package br.com.exemplo.animais;

public abstract class Animal {
    /* ---------- Atributos ---------- */
    private String nome;
    private int idade;
    private Som som;          // dependência de som
    private Alimento alimento; // dependência de alimento

    /* ---------- Construtor ---------- */
    public Animal(String nome, int idade, Som som) {
        this.nome = nome;
        this.idade = idade;
        this.som = som;
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

    /* ---------- Métodos abstratos ---------- */
    /** Faz o animal emitir seu som característico. */
    public abstract void emitirSom();

    /** Faz o animal se mover de alguma forma. */
    public abstract void movimentar();

    /* ---------- Métodos auxiliares ---------- */
    /** Alimenta o animal com o alimento fornecido. */
    public void comer(Alimento alimento) {
        this.alimento = alimento;
        System.out.println(nome + " está comendo " + alimento.getNome() + ".");
    }
}
```

```java
// ------------------------------------------------------------
// 3.  Classe Filha – Cachorro
// ------------------------------------------------------------
package br.com.exemplo.animais;

public class Cachorro extends Animal {

    /* ---------- Atributos específicos ---------- */
    private String raca;

    /* ---------- Construtor ---------- */
    public Cachorro(String nome, int idade, Som som, String raca) {
        super(nome, idade, som);
        this.raca = raca;
    }

    /* ---------- Getters / Setters ---------- */
    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    /* ---------- Implementação dos métodos abstratos ---------- */
    @Override
    public void emitirSom() {
        // Usa a dependência Som para reproduzir o latido
        String somLatido = getSom().reproduzir();
        System.out.println(getNome() + " (cachorro) diz: " + somLatido);
    }

    @Override
    public void movimentar() {
        System.out.println(getNome() + " corre alegremente.");
    }
}
```

```java
// ------------------------------------------------------------
// 4.  Classe Filha – Gato
// ------------------------------------------------------------
package br.com.exemplo.animais;

public class Gato extends Animal {

    /* ---------- Atributos específicos ---------- */
    private boolean temPeloCurto;

    /* ---------- Construtor ---------- */
    public Gato(String nome, int idade, Som som, boolean temPeloCurto) {
        super(nome, idade, som);
        this.temPeloCurto = temPeloCurto;
    }

    /* ---------- Getters / Setters ---------- */
    public boolean isTemPeloCurto() {
        return temPeloCurto;
    }

    public void setTemPeloCurto(boolean temPeloCurto) {
        this.temPeloCurto = temPeloCurto;
    }

    /* ---------- Implementação dos métodos abstratos ---------- */
    @Override
    public void emitirSom() {
        // Usa a dependência Som para reproduzir o miado
        String somMiado = getSom().reproduzir();
        System.out.println(getNome() + " (gato) diz: " + somMiado);
    }

    @Override
    public void movimentar() {
        System.out.println(getNome() + " se move com elegância.");
    }
}
```

### Como usar

```java
package br.com.exemplo;

import br.com.exemplo.animais.*;

public class Main {
    public static void main(String[] args) {
        Som somLatido = new Som();   // stub – retornará string vazia
        Som somMiado = new Som();    // stub – retornará string vazia

        Cachorro cachorro = new Cachorro("Rex", 4, somLatido, "Pastor Alemão");
        Gato gato = new Gato("Mia", 2, somMiado, true);

        cachorro.emitirSom();   // Rex diz: 
        cachorro.movimentar();  // Rex corre alegremente.
        cachorro.comer(new Alimento("ração"));

        gato.emitirSom();       // Mia diz: 
        gato.movimentar();      // Mia se move com elegância.
        gato.comer(new Alimento("peixe"));
    }
}
```

> **Observação**  
> Os objetos `Som` e `Alimento` são apenas *stubs* para que o código compile.  
> Em um cenário real, eles conteriam lógica de reprodução de som e de propriedades nutricionais, respectivamente.  
> A estrutura acima garante que a hierarquia principal (`Animal`, `Cachorro`, `Gato`) esteja completa, com atributos, construtores, getters/setters e implementação dos métodos abstratos.