**Hierarquia principal**

```java
// FormaGeometrica.java
public abstract class FormaGeometrica {

    /** Cor que será usada para desenhar a forma */
    protected Cor cor;

    public FormaGeometrica(Cor cor) {
        this.cor = cor;
    }

    /** Calcula a área da forma */
    public abstract double calcularArea();

    /** Desenha a forma (exemplo simples) */
    public abstract void desenhar();

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }
}```

```java

// Retangulo.java
public class Retangulo extends FormaGeometrica {

    /** Ponto que representa o canto superior‑esquerdo */
    private Ponto2D origem;

    /** Largura do retângulo */
    private double largura;

    /** Altura do retângulo */
    private double altura;

    public Retangulo(Ponto2D origem, double largura, double altura, Cor cor) {
        super(cor);
        this.origem = origem;
        this.largura = largura;
        this.altura = altura;
    }

    @Override
    public double calcularArea() {
        return largura * altura;
    }

    @Override
    public void desenhar() {
        System.out.printf(
                "Desenhando retângulo na cor %s: origem=(%.2f, %.2f), largura=%.2f, altura=%.2f%n",
                cor, origem.getX(), origem.getY(), largura, altura);
    }

    // Getters e setters
    public Ponto2D getOrigem() {
        return origem;
    }

    public void setOrigem(Ponto2D origem) {
        this.origem = origem;
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        return String.format(
                "Retangulo[origem=%s, largura=%.2f, altura=%.2f, cor=%s]",
                origem, largura, altura, cor);
    }
}```

```java

// Circulo.java
public class Circulo extends FormaGeometrica {

    /** Centro do círculo */
    private Ponto2D centro;

    /** Raio do círculo */
    private double raio;

    public Circulo(Ponto2D centro, double raio, Cor cor) {
        super(cor);
        this.centro = centro;
        this.raio = raio;
    }

    @Override
    public double calcularArea() {
        return Math.PI * raio * raio;
    }

    @Override
    public void desenhar() {
        System.out.printf(
                "Desenhando círculo na cor %s: centro=(%.2f, %.2f), raio=%.2f%n",
                cor, centro.getX(), centro.getY(), raio);
    }

    // Getters e setters
    public Ponto2D getCentro() {
        return centro;
    }

    public void setCentro(Ponto2D centro) {
        this.centro = centro;
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    @Override
    public String toString() {
        return String.format(
                "Circulo[centro=%s, raio=%.2f, cor=%s]",
                centro, raio, cor);
    }
}```

**Dependências(stubs)**

```java

// Ponto2D.java
public class Ponto2D {

    private double x;
    private double y;

    public Ponto2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
}```

```java

// Cor.java
public class Cor {

    private String nome; // Ex.: "vermelho", "#FF0000"

    public Cor(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}```

Esses arquivos
compõem a

hierarquia principal (`FormaGeometrica`, `Retangulo`, `Circulo`) e os

stubs necessários (`Ponto2D`, `Cor`) para que o projeto compile e possa ser testado.