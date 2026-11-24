package br.edu.ifsudestemg.doo.groq.dispostitivosEletronicos;

public abstract class DispositivoEletronico {
    private String modelo;
    private String marca;
    private Bateria bateria;
    private Fabricante fabricante;

    public DispositivoEletronico(String modelo, String marca, Bateria bateria, Fabricante fabricante) {
        this.modelo = modelo;
        this.marca = marca;
        this.bateria = bateria;
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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
        bateria.recharge();
    }

    /**
     * Retorna especificações de hardware portátil do dispositivo.
     */
    public String especificacoesHardwarePortatil() {
        return "Modelo: " + modelo + ", Marca: " + marca + ", Fabricante: " + fabricante.getName();
    }

    @Override
    public String toString() {
        return "DispositivoEletronico{" +
                "modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", bateria=" + bateria +
                ", fabricante=" + fabricante +
                '}';
    }
}