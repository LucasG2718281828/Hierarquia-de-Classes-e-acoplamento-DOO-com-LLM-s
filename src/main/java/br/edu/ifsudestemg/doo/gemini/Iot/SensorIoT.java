package br.edu.ifsudestemg.doo.gemini.Iot;

public abstract class SensorIoT {
    protected ConexaoMqtt conexaoMqtt;
    protected Calibrador calibrador;
    protected LogManager logManager;

    public SensorIoT(ConexaoMqtt conexaoMqtt, Calibrador calibrador, LogManager logManager) {
        this.conexaoMqtt = conexaoMqtt;
        this.calibrador = calibrador;
        this.logManager = logManager;
    }

    /**
     * Realiza a calibração do sensor.
     */
    public void calibrar() {
        logManager.iniciar("Calibracao");
        calibrador.calibrar(this);
        logManager.finalizar("Calibracao");
    }

    /**
     * Transmite a leitura do sensor via MQTT.
     *
     * @param valor Valor da leitura a ser transmitida.
     */
    public void transmitirLeitura(double valor) {
        logManager.iniciar("Transmissao");
        PayloadTelemetria payload = new PayloadTelemetria(getTipoSensor(), valor);
        String topico = getTopicoMqtt();
        conexaoMqtt.publicar(topico, payload.toJson());
        logManager.finalizar("Transmissao");
    }

    /**
     * @return Tipo do sensor (ex.: "temperatura", "pressao").
     */
    protected abstract String getTipoSensor();

    /**
     * @return Tópico MQTT onde o payload será publicado.
     */
    protected abstract String getTopicoMqtt();
}