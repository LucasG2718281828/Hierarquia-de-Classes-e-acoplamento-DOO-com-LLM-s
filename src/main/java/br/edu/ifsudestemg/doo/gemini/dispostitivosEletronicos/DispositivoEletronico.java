package br.edu.ifsudestemg.doo.gemini.dispostitivosEletronicos;

public abstract class DispositivoEletronico {
    protected String modelo;
    protected String sistemaOperacional;
    protected Bateria bateria;
    protected Fabricante fabricante;

    public DispositivoEletronico(String modelo, String sistemaOperacional, Bateria bateria, Fabricante fabricante) {
        this.modelo = modelo;
        this.sistemaOperacional = sistemaOperacional;
        this.bateria = bateria;
        this.fabricante = fabricante;
    }

    // Gerencia o ciclo de bateria delegando para a classe Bateria
    public void carregarBateria(int percentual) {
        bateria.carregar(percentual);
    }

    public void descarregarBateria(int percentual) {
        bateria.descarregar(percentual);
    }

    public int getNivelBateria() {
        return bateria.getNivelAtual();
    }

    // Retorna especificações de hardware portátil
    public String getEspecificacoesHardware() {
        StringBuilder sb = new StringBuilder();
        sb.append("Modelo: ").append(modelo).append("\n");
        sb.append("SO: ").append(sistemaOperacional).append("\n");
        sb.append("Fabricante: ").append(fabricante.getNome()).append("\n");
        sb.append("Bateria: ").append(bateria.getCapacidade()).append("mAh, nível atual ")
          .append(bateria.getNivelAtual()).append("%\n");
        return sb.toString();
    }

    // Getters e Setters
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
}