**Hierarquia principal (com código completo)**  

```java
// ---------------------------------------------------------------------------
// 1. FormaGeometrica (classe abstrata)
// ---------------------------------------------------------------------------

/**
 * Classe abstrata que representa uma forma geométrica.
 * Define os métodos que todas as formas devem implementar.
 */
public abstract class FormaGeometrica {

    /** Cor da forma (dependência) */
    protected Cor cor;

    /**
     * Construtor.
     *
     * @param cor a cor da forma
     */
    public FormaGeometrica(Cor cor) {
        this.cor = cor;
    }

    /**
     * Calcula a área da forma.
     *
     * @return área em unidades quadradas
     */
    public abstract double calcularArea();

    /**
     * Desenha a forma (exemplo simples usando System.out).
     */
    public abstract void desenhar();

    /* ---------- getters e setters ---------- */

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }
}
```

```java
// ---------------------------------------------------------------------------
// 2. Retangulo (classe filha)
// ---------------------------------------------------------------------------

/**
 * Representa um retângulo definido por seu canto superior esquerdo,
 * largura e altura.
 */
public class Retangulo extends FormaGeometrica {

    /** Ponto que representa o canto superior esquerdo (dependência) */
    private Ponto2D superiorEsquerdo;

    /** Largura do retângulo */
    private double largura;

    /** Altura do retângulo */
    private double altura;

    /**
     * Construtor.
     *
     * @param superiorEsquerdo ponto do canto superior esquerdo
     * @param largura           largura do retângulo
     * @param altura            altura do retângulo
     * @param cor               cor da forma
     */
    public Retangulo(Ponto2D superiorEsquerdo, double largura, double altura, Cor cor) {
        super(cor);
        this.superiorEsquerdo = superiorEsquerdo;
        this.largura = largura;
        this.altura = altura;
    }

    /* ---------- implementação dos métodos abstratos ---------- */

    @Override
    public double calcularArea() {
        return largura * altura;
    }

    @Override
    public void desenhar() {
        System.out.println("Desenhando retângulo na cor " + cor +
                " com largura " + largura + " e altura " + altura +
                " a partir do ponto " + superiorEsquerdo);
    }

    /* ---------- getters e setters ---------- */

    public Ponto2D getSuperiorEsquerdo() {
        return superiorEsquerdo;
    }

    public void setSuperiorEsquerdo(Ponto2D superiorEsquerdo) {
        this.superiorEsquerdo = superiorEsquerdo;
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
}
```

```java
// ---------------------------------------------------------------------------
// 3. Circulo (classe filha)
// ---------------------------------------------------------------------------

/**
 * Representa um círculo definido por seu centro e raio.
 */
public class Circulo extends FormaGeometrica {

    /** Centro do círculo (dependência) */
    private Ponto2D centro;

    /** Raio do círculo */
    private double raio;

    /**
     * Construtor.
     *
     * @param centro centro do círculo
     * @param raio   raio do círculo
     * @param cor    cor da forma
     */
    public Circulo(Ponto2D centro, double raio, Cor cor) {
        super(cor);
        this.centro = centro;
        this.raio = raio;
    }

    /* ---------- implementação dos métodos abstratos ---------- */

    @Override
    public double calcularArea() {
        return Math.PI * raio * raio;
    }

    @Override
    public void desenhar() {
        System.out.println("Desenhando círculo na cor " + cor +
                " com raio " + raio + " e centro " + centro);
    }

    /* ---------- getters e setters ---------- */

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
}
```

---

**Dependências (stubs – estrutura mínima para compilação)**  

```java
// ---------------------------------------------------------------------------
// 4. Ponto2D (stub)
// ---------------------------------------------------------------------------

/**
 * Representa um ponto no plano 2D.
 * Apenas estrutura mínima para que o código compile.
 */
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
        return "(" + x + ", " + y + ")";
    }
}
```

```java
// ---------------------------------------------------------------------------
// 5. Cor (stub)
// ---------------------------------------------------------------------------

/**
 * Representa uma cor.
 * Apenas estrutura mínima para que o código compile.
 */
public class Cor {

    private