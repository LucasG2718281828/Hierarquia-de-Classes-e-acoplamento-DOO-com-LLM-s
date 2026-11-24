```java
/* ==========================================================
 *  DispositivoEletronico.java
 *  ---------------------------------
 *  Classe base que representa qualquer aparelho eletrônico
 *  portátil. Ela encapsula a bateria e o fabricante, e
 *  fornece métodos genéricos para gerenciar o ciclo de
 *  bateria e obter especificações de hardware.
 * ==========================================================
 */
public class DispositivoEletronico {

    /* ---------- Atributos ---------- */
    private String id;                     // Identificador único
    private String modelo;                 // Nome/modelo do aparelho
    private Bateria bateria;               // Dependência de bateria
    private Fabricante fabricante;         // Dependência de fabricante
    private java.util.Map<String, String> especificacoesHardware; // Map de specs

    /* ---------- Construtor ---------- */
    public DispositivoEletronico(String id, String modelo,
                                 Bateria bateria, Fabricante fabricante) {
        this.id = id;
        this.modelo = modelo;
        this.bateria = bateria;
        this.fabricante = fabricante;
        this.especificacoesHardware = new java.util.HashMap<>();
    }

    /* ---------- Métodos de Gerenciamento de Bateria ---------- */
    /**
     * Carrega a bateria até 100%.
     */
    public void carregar() {
        bateria.carregar();
    }

    /**
     * Descarrega a bateria em 10% (exemplo simples).
     */
    public void descarregar() {
        bateria.descarregar(10);
    }

    /**
     * Retorna o nível atual da bateria em porcentagem.
     */
    public int getNivelBateria() {
        return bateria.getNivel();
    }

    /* ---------- Métodos de Especificações de Hardware ---------- */
    /**
     * Adiciona ou atualiza uma especificação de hardware.
     */
    public void setEspecificacao(String chave, String valor) {
        especificacoesHardware.put(chave, valor);
    }

    /**
     * Obtém o valor de uma especificação de hardware.
     */
    public String getEspecificacao(String chave) {
        return especificacoesHardware.get(chave);
    }

    /**
     * Retorna todas as especificações de hardware.
     */
    public java.util.Map<String, String> getEspecificacoesHardware() {
        return java.util.Collections.unmodifiableMap(especificacoesHardware);
    }

    /* ---------- Getters e Setters ---------- */
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Bateria getBateria() { return bateria; }
    public void setBateria(Bateria bateria) { this.bateria = bateria; }

    public Fabricante getFabricante() { return fabricante; }
    public void setFabricante(Fabricante fabricante) { this.fabricante = fabricante; }

    /* ---------- Representação em String ---------- */
    @Override
    public String toString() {
        return String.format(
            "DispositivoEletronico{id='%s', modelo='%s', fabricante='%s', bateria=%s, specs=%s}",
            id, modelo, fabricante, bateria, especificacoesHardware);
    }
}

/* ==========================================================
 *  Smartphone.java
 *  ---------------------------------
 *  Subclasse de DispositivoEletronico que adiciona
 *  características típicas de um smartphone.
 * ==========================================================
 */
public class Smartphone extends DispositivoEletronico {

    /* ---------- Atributos Específicos ---------- */
    private int cameraResolutionMegapixels;   // Resolução da câmera principal
    private int numeroCameras;                // Quantidade de câmeras
    private boolean tem5G;                    // Suporte a 5G

    /* ---------- Construtor ---------- */
    public Smartphone(String id, String modelo,
                      Bateria bateria, Fabricante fabricante,
                      int cameraResolutionMegapixels, int numeroCameras,
                      boolean tem5G) {
        super(id, modelo, bateria, fabricante);
        this.cameraResolutionMegapixels = cameraResolutionMegapixels;
        this.numeroCameras = numeroCameras;
        this.tem5G = tem5G;
    }

    /* ---------- Getters e Setters ---------- */
    public int getCameraResolutionMegapixels() {
        return cameraResolutionMegapixels;
    }

    public void setCameraResolutionMegapixels(int cameraResolutionMegapixels) {
        this.cameraResolutionMegapixels = cameraResolutionMegapixels;
    }

    public int getNumeroCameras() {
        return numeroCameras;
    }

    public void setNumeroCameras(int numeroCameras) {
        this.numeroCameras = numeroCameras;
    }

    public boolean isTem5G() {
        return tem5G;
    }

    public void setTem5G(boolean tem5G) {
        this.tem5G = tem5G;
    }

    /* ---------- Representação em String ---------- */
    @Override
    public String toString() {
        return String.format(
            "Smartphone{%s, cameraResolution=%dMP, numCameras=%d, 5G=%b}",
            super.toString(), cameraResolutionMegapixels, numeroCameras, tem5G);
    }
}

/* ==========================================================
 *  Tablet.java
 *  ---------------------------------
 *  Subclasse de DispositivoEletronico que adiciona
 *  características típicas de um tablet.
 * ==========================================================
 */
public class Tablet extends DispositivoEletronico {

    /* ---------- Atributos Específicos ---------- */
    private double screenSizeInches;   // Tamanho da tela em polegadas
    private boolean suportaStylus;     // Suporte a caneta digital

    /* ---------- Construtor ---------- */
    public Tablet(String id, String modelo,
                  Bateria bateria, Fabricante fabricante,
                  double screenSizeInches, boolean suportaStylus) {
        super(id, modelo, bateria, fabricante);
        this.screenSizeInches = screenSizeInches;
        this.suportaStylus = suportaStylus;
    }

    /* ---------- Getters e Setters ---------- */
    public double getScreenSizeInches() {
        return screenSizeInches;
    }

    public void setScreenSizeInches(double screenSizeInches) {
        this.screenSizeInches = screenSizeInches;
    }

    public boolean isSuportaStylus() {
        return suportaStylus;
    }

    public void setSuportaStylus(boolean suportaStylus) {
        this.suportaStylus = suportaStylus;
    }

    /* ---------- Representação em String ---------- */
    @Override
    public String toString() {
        return String.format(
            "Tablet{%s, screenSize=%.1f\", stylus=%b}",
            super.toString(), screenSizeInches, suportaStylus);
    }
}

/* ==========================================================
 *  Bateria.java
 *  ---------------------------------
 *  Dependência simples que representa a bateria de um
 *  dispositivo eletrônico. Apenas métodos vazios para
 *  compilar.
 * ==========================================================
 */
public class Bateria {

    private int nivel; // 0 a 100

    public Bateria() {
        this.nivel = 100;
    }

    public void carregar() {
        // Implementação fictícia: carrega a bateria ao máximo
        this.nivel = 100;
    }

    public void descarregar(int porcentagem) {
        // Implementação fictícia: reduz a bateria em 'porcentagem'%
        this.nivel = Math.max(0, this.nivel - porcentagem);
    }

    public int getNivel() {
        return this.nivel;
    }

    @Override
    public String toString() {
        return String.format("Bateria{nivel=%d%%}", nivel);
    }
}

/* ==========================================================
 *  Fabricante.java
 *  ---------------------------------
 *  Dependência simples que representa o fabricante de
 *  um dispositivo eletrônico. Apenas métodos vazios para
 *  compilar.
 * ==========================================================
 */
public class Fabricante {

    private String nome;
    private String pais;

    public Fabricante(String nome, String pais) {
        this.nome = nome;
        this.pais = pais;
    }

    public String getNome()