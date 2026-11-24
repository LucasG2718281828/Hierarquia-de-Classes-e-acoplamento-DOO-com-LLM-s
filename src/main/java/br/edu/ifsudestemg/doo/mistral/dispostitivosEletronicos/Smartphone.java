package br.edu.ifsudestemg.doo.mistral.dispostitivosEletronicos;

public class Smartphone extends DispositivoEletronico {
    private int cameraMegapixels;
    private boolean tem5G;

    public Smartphone(String modelo, String sistemaOperacional, Bateria bateria, Fabricante fabricante,
                      int cameraMegapixels, boolean tem5G) {
        super(modelo, sistemaOperacional, bateria, fabricante);
        this.cameraMegapixels = cameraMegapixels;
        this.tem5G = tem5G;
    }

    public int getCameraMegapixels() {
        return cameraMegapixels;
    }

    public void setCameraMegapixels(int cameraMegapixels) {
        this.cameraMegapixels = cameraMegapixels;
    }

    public boolean isTem5G() {
        return tem5G;
    }

    public void setTem5G(boolean tem5G) {
        this.tem5G = tem5G;
    }

    @Override
    public String especificacoesHardwarePortatil() {
        StringBuilder sb = new StringBuilder();
        sb.append("Smartphone Modelo: ").append(getModelo()).append("\n");
        sb.append("Sistema Operacional: ").append(getSistemaOperacional()).append("\n");
        sb.append("Fabricante: ").append(getFabricante().getNome()).append("\n");
        sb.append("Bateria Capacidade: ").append(getBateria().getCapacidade()).append(" mAh\n");
        sb.append("Câmera: ").append(cameraMegapixels).append(" MP\n");
        sb.append("5G: ").append(tem5G ? "Sim" : "Não").append("\n");
        return sb.toString();
    }
}