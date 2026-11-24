package br.edu.ifsudestemg.doo.mistral.dispostitivosEletronicos;

public abstract class DispositivoEletronico {
    private Bateria bateria;
    private Fabricante fabricante;
    private String modelo;
    private String sistemaOperacional;

    public DispositivoEletronico(String modelo, String sistemaOperacional, Bateria bateria, Fabricante fabricante) {
        this.modelo = modelo;
        this.sistemaOperacional = sistemaOperacional;
        this.bateria = bateria;
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public Bateria getBateria() {
        return bateria;
    }

    public void setBateria(Bateria bateria) {
        this.bateria = bateria;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    /**
     * Gerencia o ciclo de bateria do dispositivo.
     */
    public void gerenciarCicloBateria() {
        // Lógica simplificada para gerenciar o ciclo de bateria
        System.out.println("Gerenciando ciclo de bateria para " + modelo);
        bateria.carregar();
    }

    /**
     * Retorna especificações de hardware portátil do dispositivo.
     * @return String com as especificações.
     */
    public abstract String especificacoesHardwarePortatil();
}