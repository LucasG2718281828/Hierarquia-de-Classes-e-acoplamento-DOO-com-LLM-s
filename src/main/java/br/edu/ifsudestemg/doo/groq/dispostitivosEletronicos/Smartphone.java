package br.edu.ifsudestemg.doo.groq.dispostitivosEletronicos;

public class Smartphone extends DispositivoEletronico {
    private int cameraResolution; // em megapixels

    public Smartphone(String modelo, String marca, Bateria bateria, Fabricante fabricante, int cameraResolution) {
        super(modelo, marca, bateria, fabricante);
        this.cameraResolution = cameraResolution;
    }

    public int getCameraResolution() {
        return cameraResolution;
    }

    public void setCameraResolution(int cameraResolution) {
        this.cameraResolution = cameraResolution;
    }

    @Override
    public String especificacoesHardwarePortatil() {
        return super.especificacoesHardwarePortatil() +
                ", Camera: " + cameraResolution + "MP";
    }

    public void ligarApp(String appName) {
        System.out.println("Ligando aplicativo " + appName + " no smartphone " + getModelo());
    }

    @Override
    public String toString() {
        return "Smartphone{" +
                "modelo='" + getModelo() + '\'' +
                ", marca='" + getMarca() + '\'' +
                ", cameraResolution=" + cameraResolution +
                ", bateria=" + getBateria() +
                ", fabricante=" + getFabricante() +
                '}';
    }
}