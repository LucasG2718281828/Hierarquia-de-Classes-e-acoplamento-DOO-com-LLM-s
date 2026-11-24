package br.edu.ifsudestemg.doo.mistral.Iot;

public class SensorPressao extends SensorIoT {
    private double pressao;

    public SensorPressao(String id, String location, double pressao) {
        super(id, location);
        this.pressao = pressao;
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
        // Calibração específica para pressão
        logManager.log("SensorPressao " + id + " calibrado com pressão: " + pressao);
    }

    @Override
    public void transmitir() {
        String payload = payloadTelemetria.buildPayload(this);
        conexaoMqtt.publish(payload);
        logManager.log("SensorPressao " + id + " transmitido com pressão: " + pressao);
    }

    @Override
    public String getSensorData() {
        return "Pressao:" + pressao;
    }
}