package br.edu.ifsudestemg.doo.mistral.dispostitivosEletronicos;

public class Tablet extends DispositivoEletronico {
    private double telaInch;
    private boolean temStylus;

    public Tablet(String modelo, String sistemaOperacional, Bateria bateria, Fabricante fabricante,
                  double telaInch, boolean temStylus) {
        super(modelo, sistemaOperacional, bateria, fabricante);
        this.telaInch = telaInch;
        this.temStylus = temStylus;
    }

    public double getTelaInch() {
        return telaInch;
    }

    public void setTelaInch(double telaInch) {
        this.telaInch = telaInch;
    }

    public boolean isTemStylus() {
        return temStylus;
    }

    public void setTemStylus(boolean temStylus) {
        this.temStylus = temStylus;
    }

    @Override
    public String especificacoesHardwarePortatil() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tablet Modelo: ").append(getModelo()).append("\n");
        sb.append("Sistema Operacional: ").append(getSistemaOperacional()).append("\n");
        sb.append("Fabricante: ").append(getFabricante().getNome()).append("\n");
        sb.append("Bateria Capacidade: ").append(getBateria().getCapacidade()).append(" mAh\n");
        sb.append("Tela: ").append(telaInch).append(" inch\n");
        sb.append("Stylus: ").append(temStylus ? "Sim" : "Não").append("\n");
        return sb.toString();
    }
}