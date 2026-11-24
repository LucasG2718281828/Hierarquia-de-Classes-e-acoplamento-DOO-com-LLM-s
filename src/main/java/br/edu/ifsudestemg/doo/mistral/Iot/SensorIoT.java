package br.edu.ifsudestemg.doo.mistral.Iot;

public abstract class SensorIoT {
    protected String id;
    protected String location;
    protected ConexaoMqtt conexaoMqtt;
    protected Calibrador calibrador;
    protected LogManager logManager;
    protected PayloadTelemetria payloadTelemetria;

    public SensorIoT(String id, String location) {
        this.id = id;
        this.location = location;
        this.conexaoMqtt = new ConexaoMqtt();
        this.calibrador = new Calibrador();
        this.logManager = new LogManager();
        this.payloadTelemetria = new PayloadTelemetria();
    }

    public void calibrar() {
        calibrador.calibrarSensor(this);
        logManager.log("Sensor " + id + " calibrado.");
    }

    public void transmitir() {
        String payload = payloadTelemetria.buildPayload(this);
        conexaoMqtt.publish(payload);
        logManager.log("Sensor " + id + " transmitido com payload: " + payload);
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    // Método que pode ser sobrescrito pelas subclasses para fornecer dados específicos
    public abstract String getSensorData();
}