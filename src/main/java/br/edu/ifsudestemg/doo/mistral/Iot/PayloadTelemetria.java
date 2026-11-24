package br.edu.ifsudestemg.doo.mistral.Iot;

public class PayloadTelemetria {
    public String buildPayload(SensorIoT sensor) {
        // Implementação stub
        return sensor.getId() + "|" + sensor.getLocation() + "|" + sensor.getSensorData();
    }
}