**Hierarquia principal (com atributos e métodos completos)**  

```java
// ---------------------------------------------------------------------------
//  DispositivoEletronico.java
// ---------------------------------------------------------------------------

/**
 * Classe abstrata que representa um dispositivo eletrônico portátil.
 * Ela encapsula a lógica de gerenciamento de bateria e especificações de hardware.
 */
public abstract class DispositivoEletronico {

    /* -----------------------------------------------------------------------
     *  Atributos
     * ----------------------------------------------------------------------- */
    protected String modelo;          // Nome/modelo do dispositivo
    protected Bateria bateria;        // Bateria do dispositivo
    protected Fabricante fabricante;  // Fabricante do dispositivo
    protected int cicloBateria;       // Número de ciclos de carga já realizados

    /* -----------------------------------------------------------------------
     *  Construtor
     * ----------------------------------------------------------------------- */
    public DispositivoEletronico(String modelo, Bateria bateria, Fabricante fabricante) {
        this.modelo = modelo;
        this.bateria = bateria;
        this.fabricante = fabricante;
        this.cicloBateria = 0; // inicia com zero ciclos
    }

    /* -----------------------------------------------------------------------
     *  Métodos de gerenciamento de bateria
     * ----------------------------------------------------------------------- */
    /**
     * Carrega a bateria do dispositivo.
     */
    public void carregar() {
        bateria.carregar();
        atualizarCicloBateria();
    }

    /**
     * Descarrega a bateria do dispositivo.
     */
    public void descarregar() {
        bateria.descarregar();
    }

    /**
     * Retorna o número atual de ciclos de carga da bateria.
     */
    public int getCicloBateria() {
        return cicloBateria;
    }

    /**
     * Incrementa o contador de ciclos de bateria.
     */
    protected void atualizarCicloBateria() {
        this.cicloBateria++;
    }

    /* -----------------------------------------------------------------------
     *  Métodos de especificação de hardware
     * ----------------------------------------------------------------------- */
    /**
     * Retorna uma descrição textual das especificações de hardware do dispositivo.
     */
    public String getEspecificacoesHardware() {
        return String.format(
                "Modelo: %s | Fabricante: %s | Ciclos de bateria: %d",
                modelo,
                fabricante.getNome(),
                cicloBateria
        );
    }

    /* -----------------------------------------------------------------------
     *  Métodos abstratos (para que as subclasses forneçam detalhes específicos)
     * ----------------------------------------------------------------------- */
    /**
     * Retorna o tipo de dispositivo (ex.: "Smartphone", "Tablet").
     */
    public abstract String getTipoDispositivo();

    /**
     * Retorna informações adicionais específicas do tipo de dispositivo.
     */
    public abstract String getInformacoesEspecificas();
}
```

```java
// ---------------------------------------------------------------------------
//  Smartphone.java
// ---------------------------------------------------------------------------

/**
 * Representa um smartphone, que é um tipo de dispositivo eletrônico portátil.
 */
public class Smartphone extends DispositivoEletronico {

    /* -----------------------------------------------------------------------
     *  Atributos específicos de Smartphone
     * ----------------------------------------------------------------------- */
    private int cameraResolution; // em megapixels
    private boolean tem5G;        // indica se o smartphone suporta 5G

    /* -----------------------------------------------------------------------
     *  Construtor
     * ----------------------------------------------------------------------- */
    public Smartphone(String modelo, Bateria bateria, Fabricante fabricante,
                      int cameraResolution, boolean tem5G) {
        super(modelo, bateria, fabricante);
        this.cameraResolution = cameraResolution;
        this.tem5G = tem5G;
    }

    /* -----------------------------------------------------------------------
     *  Métodos específicos de Smartphone
     * ----------------------------------------------------------------------- */
    public int getCameraResolution() {
        return cameraResolution;
    }

    public boolean isTem5G() {
        return tem5G;
    }

    /* -----------------------------------------------------------------------
     *  Implementação dos métodos abstratos da superclasse
     * ----------------------------------------------------------------------- */
    @Override
    public String getTipoDispositivo() {
        return "Smartphone";
    }

    @Override
    public String getInformacoesEspecificas() {
        return String.format(
                "Camera: %d MP | 5G: %s",
                cameraResolution,
                tem5G ? "Sim" : "Não"
        );
    }
}
```

```java
// ---------------------------------------------------------------------------
//  Tablet.java
// ---------------------------------------------------------------------------

/**
 * Representa um tablet, que é um tipo de dispositivo eletrônico portátil.
 */
public class Tablet extends DispositivoEletronico {

    /* -----------------------------------------------------------------------
     *  Atributos específicos de Tablet
     * ----------------------------------------------------------------------- */
    private double screenSize;   // tamanho da tela em polegadas
    private boolean suportaStylus; // indica se o tablet suporta stylus

    /* -----------------------------------------------------------------------
     *  Construtor
     * ----------------------------------------------------------------------- */
    public Tablet(String modelo, Bateria bateria, Fabricante fabricante,
                  double screenSize, boolean suportaStylus) {
        super(modelo, bateria, fabricante);
        this.screenSize = screenSize;
        this.suportaStylus = suportaStylus;
    }

    /* -----------------------------------------------------------------------
     *  Métodos específicos de Tablet
     * ----------------------------------------------------------------------- */
    public double getScreenSize() {
        return screenSize;
    }

    public boolean isSuportaStylus() {
        return suportaStylus;
    }

    /* -----------------------------------------------------------------------
     *  Implementação dos métodos abstratos da superclasse
     * ----------------------------------------------------------------------- */
    @Override
    public String getTipoDispositivo() {
        return "Tablet";
    }

    @Override
    public String getInformacoesEspecificas() {
        return String.format(
                "Tamanho da tela: %.1f\" | Stylus: %s",
                screenSize,
                suportaStylus ? "Sim" : "Não"
        );
    }
}
```

**Dependências (esqueleto mínimo para compilação)**  

```java
// ---------------------------------------------------------------------------
//  Bateria.java
// ---------------------------------------------------------------------------

/**
 * Representa a bateria de um dispositivo eletrônico.
 * Este é apenas um esqueleto para que o código compile.
 */
public class Bateria {

    private int capacidade; // em mAh
    private int nivel;      // nível atual da bateria (0-100)

    public Bateria(int capacidade) {
        this.capacidade = capacidade;
        this.nivel = 100; // inicia totalmente carregada
    }

    public void carregar() {
        // Lógica fictícia de carregamento
        this.nivel = 100;
    }

    public void descarregar() {
        // Lógica fictícia de descarga
        this.nivel = 0;
    }

    public int getNivel() {
        return nivel;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
```

```java
// ---------------------------------------------------------------------------
//  Fabricante.java
// ---------------------------------------------------------------------------

/**
 * Representa o fabricante de um dispositivo eletrônico.
 * Este é apenas um esqueleto para que o código compile.
 */
public class Fabricante {

    private String nome;
    private String pais;

    public Fabricante(String nome, String pais) {
        this.nome = nome;
        this.pais = pais;
    }

    public String getNome() {
        return nome;
    }

    public String getPais() {
        return pais;
    }
}
```

> **Observações**  
> - A classe `DispositivoEletronico` é abstrata para forçar as subclasses a implementarem detalhes específicos.  
> - Os métodos de gerenciamento de bateria (`carregar`, `descarregar`) delegam a lógica para a classe `