package br.edu.ifsudestemg.doo.mistral.Iot;

public class SensorTemperatura extends SensorIoT {
    private double temperatura;

    public SensorTemperatura(String id, String location, double temperatura) {
        super(id, location);
        this.temperatura = temperatura;
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
        // Calibração específica para temperatura
        logManager.log("SensorTemperatura " + id + " calibrado com temperatura: " + temperatura);
    }

    @Override
    public void transmitir() {
        String payload = payloadTelemetria.buildPayload(this);
        conexaoMqtt.publish(payload);
        logManager.log("SensorTemperatura " + id + " transmitido com temperatura: " + temperatura);
    }

    @Override
    public String getSensorData() {
        return "Temperatura:" + temperatura;
    }
}