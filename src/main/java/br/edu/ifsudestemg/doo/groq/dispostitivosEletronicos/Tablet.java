package br.edu.ifsudestemg.doo.groq.dispostitivosEletronicos;

public class Tablet extends DispositivoEletronico {
    private double telaSize; // em polegadas

    public Tablet(String modelo, String marca, Bateria bateria, Fabricante fabricante, double telaSize) {
        super(modelo, marca, bateria, fabricante);
        this.telaSize = telaSize;
    }

    public double getTelaSize() {
        return telaSize;
    }

    public void setTelaSize(double telaSize) {
        this.telaSize = telaSize;
    }

    @Override
    public String especificacoesHardwarePortatil() {
        return super.especificacoesHardwarePortatil() +
                ", Tela: " + telaSize + "\"";
    }

    public void abrirAplicativo(String appName) {
        System.out.println("Abrindo aplicativo " + appName + " no tablet " + getModelo());
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "modelo='" + getModelo() + '\'' +
                ", marca='" + getMarca() + '\'' +
                ", telaSize=" + telaSize +
                ", bateria=" + getBateria() +
                ", fabricante=" + getFabricante() +
                '}';
    }
}