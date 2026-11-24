package br.edu.ifsudestemg.doo.gemini.Iot;

public class ConexaoMqtt {

    public ConexaoMqtt() {
        // Construtor padrão.
    }

    /**
     * Publica uma mensagem em um tópico MQTT.
     *
     * @param topico Tópico destino.
     * @param mensagem Payload em formato JSON.
     */
    public void publicar(String topico, String mensagem) {
        // Stub: implementação real enviaria a mensagem ao broker MQTT.
    }

    /**
     * Conecta ao broker MQTT.
     */
    public void conectar() {
        // Stub.
    }

    /**
     * Desconecta do broker MQTT.
     */
    public void desconectar() {
        // Stub.
    }
}