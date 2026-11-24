package br.edu.ifsudestemg.doo.gemini.Iot;

public class SensorPressao extends SensorIoT {

    private static final String TIPO = "pressao";
    private static final String TOPICO = "sensores/pressao";

    public SensorPressao(ConexaoMqtt conexaoMqtt, Calibrador calibrador, LogManager logManager) {
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
     * Simula a captura de pressão atmosférica.
     *
     * @return Valor da pressão em hPa.
     */
    public double capturarPressao() {
        // Implementação fictícia; em produção leria de hardware.
        return 1013.25;
    }

    /**
     * Captura e transmite a pressão.
     */
    public void lerETransmitir() {
        double pressao = capturarPressao();
        transmitirLeitura(pressao);
    }
}