package br.edu.ifsudestemg.doo.gemini.Iot;

public class SensorTemperatura extends SensorIoT {

    private static final String TIPO = "temperatura";
    private static final String TOPICO = "sensores/temperatura";

    public SensorTemperatura(ConexaoMqtt conexaoMqtt, Calibrador calibrador, LogManager logManager) {
        super(conexaoMqtt, calibrador, logManager);
    }

    @Override
    protected String getTipoSensor() {
        return TIPO;
    }

    @Override
    protected String getTopicoMqtt() {
        return TOPICO;
    }

    /**
     * Simula a captura de temperatura ambiente.
     *
     * @return Valor da temperatura em graus Celsius.
     */
    public double capturarTemperatura() {
        // Implementação fictícia; em produção leria de hardware.
        return 22.5;
    }

    /**
     * Captura e transmite a temperatura.
     */
    public void lerETransmitir() {
        double temperatura = capturarTemperatura();
        transmitirLeitura(temperatura);
    }
}