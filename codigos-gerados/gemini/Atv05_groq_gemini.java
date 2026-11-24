**Hierarquia principal (com atributos e métodos completos)**  

```java
// ---------------------------------------------------------------------------
// Classe base: DispositivoEletronico
// ---------------------------------------------------------------------------

/**
 * Representa um dispositivo eletrônico genérico.
 * Possui gerenciamento de ciclo de bateria e especificações de hardware.
 */
public abstract class DispositivoEletronico {

    /* -----------------------------------------------------------------------
     * Atributos
     * ----------------------------------------------------------------------- */
    protected String modelo;
    protected Bateria bateria;
    protected Fabricante fabricante;
    protected int cicloBateria;   // número de ciclos de carga já realizados

    /* -----------------------------------------------------------------------
     * Construtor
     * ----------------------------------------------------------------------- */
    public DispositivoEletronico(String modelo, Bateria bateria, Fabricante fabricante) {
        this.modelo = modelo;
        this.bateria = bateria;
        this.fabricante = fabricante;
        this.cicloBateria = 0;   // inicia com zero ciclos
    }

    /* -----------------------------------------------------------------------
     * Métodos de gerenciamento de bateria
     * ----------------------------------------------------------------------- */
    /**
     * Incrementa o contador de ciclos de bateria em 1.
     * Em um cenário real, esse método poderia verificar a saúde da bateria,
     * recalibrar, etc.
     */
    public void gerenciarCicloBateria() {
        this.cicloBateria++;
    }

    public int getCicloBateria() {
        return this.cicloBateria;
    }

    public void setCicloBateria(int ciclo) {
        if (ciclo >= 0) {
            this.cicloBateria = ciclo;
        }
    }

    /* -----------------------------------------------------------------------
     * Métodos de hardware
     * ----------------------------------------------------------------------- */
    /**
     * Retorna uma descrição resumida das especificações de hardware.
     */
    public String getHardwareSpecs() {
        return String.format("Modelo: %s | Fabricante: %s | Ciclos de bateria: %d",
                modelo, fabricante.getNome(), cicloBateria);
    }

    /* -----------------------------------------------------------------------
     * Métodos de exibição
     * ----------------------------------------------------------------------- */
    public void exibirInformacoes() {
        System.out.println("=== Informações do Dispositivo ===");
        System.out.println(getHardwareSpecs());
        System.out.println("Tipo: " + getTipoDispositivo());
        System.out.println("---------------------------------");
    }

    /* -----------------------------------------------------------------------
     * Método abstrato que deve ser implementado pelas subclasses
     * ----------------------------------------------------------------------- */
    protected abstract String getTipoDispositivo();
}
```

```java
// ---------------------------------------------------------------------------
// Subclasse: Smartphone
// ---------------------------------------------------------------------------

/**
 * Representa um smartphone, com câmeras e suporte a 5G.
 */
public class Smartphone extends DispositivoEletronico {

    /* -----------------------------------------------------------------------
     * Atributos específicos do smartphone
     * ----------------------------------------------------------------------- */
    private int numeroDeCameras;
    private boolean tem5G;

    /* -----------------------------------------------------------------------
     * Construtor
     * ----------------------------------------------------------------------- */
    public Smartphone(String modelo, Bateria bateria, Fabricante fabricante,
                      int numeroDeCameras, boolean tem5G) {
        super(modelo, bateria, fabricante);
        this.numeroDeCameras = numeroDeCameras;
        this.tem5G = tem5G;
    }

    /* -----------------------------------------------------------------------
     * Implementação do método abstrato
     * ----------------------------------------------------------------------- */
    @Override
    protected String getTipoDispositivo() {
        return "Smartphone";
    }

    /* -----------------------------------------------------------------------
     * Métodos adicionais
     * ----------------------------------------------------------------------- */
    public void exibirCameraInfo() {
        System.out.println("Câmeras: " + numeroDeCameras + " | 5G: " + (tem5G ? "Sim" : "Não"));
    }

    /* -----------------------------------------------------------------------
     * Getters e Setters
     * ----------------------------------------------------------------------- */
    public int getNumeroDeCameras() {
        return numeroDeCameras;
    }

    public void setNumeroDeCameras(int numeroDeCameras) {
        this.numeroDeCameras = numeroDeCameras;
    }

    public boolean isTem5G() {
        return tem5G;
    }

    public void setTem5G(boolean tem5G) {
        this.tem5G = tem5G;
    }
}
```

```java
// ---------------------------------------------------------------------------
// Subclasse: Tablet
// ---------------------------------------------------------------------------

/**
 * Representa um tablet, com tela de tamanho variável e suporte opcional a stylus.
 */
public class Tablet extends DispositivoEletronico {

    /* -----------------------------------------------------------------------
     * Atributos específicos do tablet
     * ----------------------------------------------------------------------- */
    private double tamanhoTela;   // em polegadas
    private boolean temStylus;

    /* -----------------------------------------------------------------------
     * Construtor
     * ----------------------------------------------------------------------- */
    public Tablet(String modelo, Bateria bateria, Fabricante fabricante,
                  double tamanhoTela, boolean temStylus) {
        super(modelo, bateria, fabricante);
        this.tamanhoTela = tamanhoTela;
        this.temStylus = temStylus;
    }

    /* -----------------------------------------------------------------------
     * Implementação do método abstrato
     * ----------------------------------------------------------------------- */
    @Override
    protected String getTipoDispositivo() {
        return "Tablet";
    }

    /* -----------------------------------------------------------------------
     * Métodos adicionais
     * ----------------------------------------------------------------------- */
    public void exibirTelaInfo() {
        System.out.println("Tamanho da tela: " + tamanhoTela + "\" | Stylus: " + (temStylus ? "Sim" : "Não"));
    }

    /* -----------------------------------------------------------------------
     * Getters e Setters
     * ----------------------------------------------------------------------- */
    public double getTamanhoTela() {
        return tamanhoTela;
    }

    public void setTamanhoTela(double tamanhoTela) {
        this.tamanhoTela = tamanhoTela;
    }

    public boolean isTemStylus() {
        return temStylus;
    }

    public void setTemStylus(boolean temStylus) {
        this.temStylus = temStylus;
    }
}
```

**Dependências (esqueleto mínimo para compilação)**  

```java
// ---------------------------------------------------------------------------
// Dependência: Bateria
// ---------------------------------------------------------------------------

/**
 * Representa a bateria de um dispositivo eletrônico.
 * Este é apenas um esqueleto para que o código compile.
 */
public class Bateria {

    private int capacidade;   // em mAh

    public Bateria(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
}
```

```java
// ---------------------------------------------------------------------------
// Dependência: Fabricante
// ---------------------------------------------------------------------------

/**
 * Representa o fabricante de um dispositivo eletrônico.
 * Este é apenas um esqueleto para que o código compile.
 */
public class Fabricante {

    private String nome;

    public Fabricante(String nome) {
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

**Como usar (exemplo de teste)**  

```java
public class Main {
    public static void main(String[] args) {
        Fabricante samsung = new Fabricante("Samsung");
