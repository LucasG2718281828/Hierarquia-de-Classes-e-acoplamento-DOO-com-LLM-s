package br.edu.ifsudestemg.doo.groq.Iot;

public class SensorTemperatura extends SensorIoT {
    private double temperatura;

    public SensorTemperatura(String id, String location, double temperaturaInicial) {
        super(id, location);
        this.temperatura = temperaturaInicial;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    @Override
    public void calibrar() {
        super.calibrar();
        // Lógica específica de calibragem de temperatura, se necessário
    }

    @Override
    public void transmitir() {
        super.transmitir();
        // Lógica adicional de transmissão, se necessário
    }
}