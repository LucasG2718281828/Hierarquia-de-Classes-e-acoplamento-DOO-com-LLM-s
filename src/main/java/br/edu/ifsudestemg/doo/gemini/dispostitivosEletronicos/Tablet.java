package br.edu.ifsudestemg.doo.gemini.dispostitivosEletronicos;

public class Tablet extends DispositivoEletronico {
    private double tamanhoTela; // em polegadas
    private boolean suporteCaneta;
    private int capacidadeArmazenamento; // em GB

    public Tablet(String modelo, String sistemaOperacional, Bateria bateria, Fabricante fabricante,
                  double tamanhoTela, boolean suporteCaneta, int capacidadeArmazenamento) {
        super(modelo, sistemaOperacional, bateria, fabricante);
        this.tamanhoTela = tamanhoTela;
        this.suporteCaneta = suporteCaneta;
        this.capacidadeArmazenamento = capacidadeArmazenamento;
    }

    public double getTamanhoTela() {
        return tamanhoTela;
    }

    public void setTamanhoTela(double tamanhoTela) {
        this.tamanhoTela = tamanhoTela;
    }

    public boolean isSuporteCaneta() {
        return suporteCaneta;
    }

    public void setSuporteCaneta(boolean suporteCaneta) {
        this.suporteCaneta = suporteCaneta;
    }

    public int getCapacidadeArmazenamento() {
        return capacidadeArmazenamento;
    }

    public void setCapacidadeArmazenamento(int capacidadeArmazenamento) {
        this.capacidadeArmazenamento = capacidadeArmazenamento;
    }

    @Override
    public String getEspecificacoesHardware() {
        String base = super.getEspecificacoesHardware();
        StringBuilder sb = new StringBuilder(base);
        sb.append("Tipo: Tablet\n");
        sb.append("Tela: ").append(tamanhoTela).append(" polegadas\n");
        sb.append("Caneta: ").append(suporteCaneta ? "Sim" : "Não").append("\n");
        sb.append("Armazenamento: ").append(capacidadeArmazenamento).append(" GB\n");
        return sb.toString();
    }
}