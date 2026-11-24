package br.edu.ifsudestemg.doo.groq.Iot;

public class SensorPressao extends SensorIoT {
    private double pressao;

    public SensorPressao(String id, String location, double pressaoInicial) {
        super(id, location);
        this.pressao = pressaoInicial;
    }

    public double getPressao() {
        return pressao;
    }

    public void setPressao(double pressao) {
        this.pressao = pressao;
    }

    @Override
    public void calibrar() {
        super.calibrar();
        // Lógica específica de calibragem de pressão, se necessário
    }

    @Override
    public void transmitir() {
        super.transmitir();
        // Lógica adicional de transmissão, se necessário
    }
}