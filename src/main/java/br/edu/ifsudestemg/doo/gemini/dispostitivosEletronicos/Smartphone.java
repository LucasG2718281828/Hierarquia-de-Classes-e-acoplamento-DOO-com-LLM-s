package br.edu.ifsudestemg.doo.gemini.dispostitivosEletronicos;

public class Smartphone extends DispositivoEletronico {
    private double tamanhoTela; // em polegadas
    private int cameraMP; // megapixels
    private boolean suporte5G;

    public Smartphone(String modelo, String sistemaOperacional, Bateria bateria, Fabricante fabricante,
                      double tamanhoTela, int cameraMP, boolean suporte5G) {
        super(modelo, sistemaOperacional, bateria, fabricante);
        this.tamanhoTela = tamanhoTela;
        this.cameraMP = cameraMP;
        this.suporte5G = suporte5G;
    }

    public double getTamanhoTela() {
        return tamanhoTela;
    }

    public void setTamanhoTela(double tamanhoTela) {
        this.tamanhoTela = tamanhoTela;
    }

    public int getCameraMP() {
        return cameraMP;
    }

    public void setCameraMP(int cameraMP) {
        this.cameraMP = cameraMP;
    }

    public boolean isSuporte5G() {
        return suporte5G;
    }

    public void setSuporte5G(boolean suporte5G) {
        this.suporte5G = suporte5G;
    }

    @Override
    public String getEspecificacoesHardware() {
        String base = super.getEspecificacoesHardware();
        StringBuilder sb = new StringBuilder(base);
        sb.append("Tipo: Smartphone\n");
        sb.append("Tela: ").append(tamanhoTela).append(" polegadas\n");
        sb.append("Câmera: ").append(cameraMP).append(" MP\n");
        sb.append("5G: ").append(suporte5G ? "Sim" : "Não").append("\n");
        return sb.toString();
    }
}