package br.edu.ifsudestemg.doo.gemini.Iot;

public class PayloadTelemetria {

    private String tipoSensor;
    private double valor;
    private long timestamp;

    public PayloadTelemetria(String tipoSensor, double valor) {
        this.tipoSensor = tipoSensor;
        this.valor = valor;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Converte o payload para JSON.
     *
     * @return Representação JSON do payload.
     */
    public String toJson() {
        // Stub simples; em produção usaria biblioteca como Gson ou Jackson.
        return String.format("{\"tipo\":\"%s\",\"valor\":%s,\"ts\":%d}",
                tipoSensor, valor, timestamp);
    }

    // Getters e Setters (opcional)
    public String getTipoSensor() {
        return tipoSensor;
    }

    public double getValor() {
        return valor;
    }

    public long getTimestamp() {
        return timestamp;
    }
}