**SensorIoT.java** – classe pai (com atributos, construtor e os métodos de calibração e transmissão)

```java
package iot.sensors;

/**
 * Classe base para sensores IoT que precisam ser calibrados e enviar leituras
 * via MQTT.  As dependências são injetadas no construtor para facilitar
 * testes e manter o acoplamento baixo.
 */
public abstract class SensorIoT {

    /* Identificador único do sensor */
    protected final String id;

    /* Localização física (ex.: “Sala 1”, “Pátio”, …) */
    protected final String location;

    /* Conexão MQTT que será usada para publicar as leituras */
    protected final ConexaoMqtt conexaoMqtt;

    /* Serviço de calibração */
    protected final Calibrador calibrador;

    /* Gerenciador de logs */
    protected final LogManager logManager;

    /* Gera o payload que será enviado */
    protected final PayloadTelemetria payloadTelemetria;

    /* Topico MQTT padrão – pode ser sobrescrito pelas subclasses */
    protected String topic = "sensors/generic";

    /**
     * Construtor que recebe todas as dependências necessárias.
     *
     * @param id                identificador do sensor
     * @param location          localização física
     * @param conexaoMqtt       conexão MQTT
     * @param calibrador        serviço de calibração
     * @param logManager        gerenciador de logs
     * @param payloadTelemetria gerador de payload
     */
    public SensorIoT(String id,
                     String location,
                     ConexaoMqtt conexaoMqtt,
                     Calibrador calibrador,
                     LogManager logManager,
                     PayloadTelemetria payloadTelemetria) {

        this.id = id;
        this.location = location;
        this.conexaoMqtt = conexaoMqtt;
        this.calibrador = calibrador;
        this.logManager = logManager;
        this.payloadTelemetria = payloadTelemetria;
    }

    /**
     * Método que realiza a calibração do sensor.  A lógica de calibração
     * fica encapsulada no objeto {@link Calibrador}.
     */
    public void calibrar() {
        logManager.log("Calibrando sensor " + id + " (" + getClass().getSimpleName() + ")");
        calibrador.calibrarSensor(this);
        logManager.log("Calibração concluída para sensor " + id);
    }

    /**
     * Publica a leitura atual do sensor no tópico MQTT.
     */
    public void transmitirLeitura() {
        String payload = payloadTelemetria.buildPayload(this);
        logManager.log("Publicando payload no tópico " + topic + ": " + payload);
        conexaoMqtt.publish(topic, payload);
    }

    /**
     * Subclasses devem implementar a lógica de leitura específica.
     *
     * @return valor da leitura (ex.: temperatura em °C, pressão em Pa, etc.)
     */
    protected abstract double obterLeitura();

    /**
     * Subclasses podem sobrescrever para alterar o tópico MQTT.
     *
     * @return tópico MQTT
     */
    protected String getTopic() {
        return topic;
    }
}
```

---

**SensorTemperatura.java** – subclasse que representa um sensor de temperatura

```java
package iot.sensors;

/**
 * Sensor de temperatura.  O tópico MQTT padrão é
 * “sensors/temperature”.
 */
public class SensorTemperatura extends SensorIoT {

    public SensorTemperatura(String id,
                             String location,
                             ConexaoMqtt conexaoMqtt,
                             Calibrador calibrador,
                             LogManager logManager,
                             PayloadTelemetria payloadTelemetria) {

        super(id, location, conexaoMqtt, calibrador, logManager, payloadTelemetria);
        this.topic = "sensors/temperature";
    }

    @Override
    protected double obterLeitura() {
        // Aqui você colocaria a lógica real de leitura do sensor.
        // Para fins de compilação, retornamos um valor fictício.
        return 23.5; // °C
    }
}
```

---

**SensorPressao.java** – subclasse que representa um sensor de pressão

```java
package iot.sensors;

/**
 * Sensor de pressão.  O tópico MQTT padrão é
 * “sensors/pressure”.
 */
public class SensorPressao extends SensorIoT {

    public SensorPressao(String id,
                         String location,
                         ConexaoMqtt conexaoMqtt,
                         Calibrador calibrador,
                         LogManager logManager,
                         PayloadTelemetria payloadTelemetria) {

        super(id, location, conexaoMqtt, calibrador, logManager, payloadTelemetria);
        this.topic = "sensors/pressure";
    }

    @Override
    protected double obterLeitura() {
        // Lógica fictícia de leitura de pressão.
        return 101325; // Pa
    }
}
```

---

## Stubs das dependências (só estrutura mínima)

**ConexaoMqtt.java**

```java
package iot.sensors;

/**
 * Stub da conexão MQTT.  Em produção, essa classe
 * encapsularia a biblioteca cliente MQTT (ex.: Eclipse Paho).
 */
public class ConexaoMqtt {

    public void connect() {
        // Conexão ao broker MQTT
    }

    public void publish(String topic, String payload) {
        // Publica o payload no tópico
    }
}
```

**Calibrador.java**

```java
package iot.sensors;

/**
 * Stub do serviço de calibração.  Em produção, a lógica
 * de calibração específica do sensor seria implementada aqui.
 */
public class Calibrador {

    public void calibrarSensor(SensorIoT sensor) {
        // Calibração do sensor
    }
}
```

**LogManager.java**

```java
package iot.sensors;

/**
 * Stub de gerenciador de logs.  Em produção, poderia usar
 * SLF4J, Log4j, etc.
 */
public class LogManager {

    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
```

**PayloadTelemetria.java**

```java
package iot.sensors;

/**
 * Stub que gera o payload JSON (ou outro formato) a ser enviado.
 */
public class PayloadTelemetria {

    public String buildPayload(SensorIoT sensor) {
        // Exemplo simples de payload
        double leitura = sensor.obterLeitura();
        return String.format("{\"id\":\"%s\",\"location\":\"%s\",\"value\":%f}",
                sensor.id, sensor.location, leitura);
    }
}
```

---

### Como usar

```java
public class Main {
    public static void main(String[] args) {
        ConexaoMqtt conexao = new ConexaoMqtt();
        Calibrador calibrador = new Calibrador();
        LogManager log = new LogManager();
        PayloadTelemetria payload = new PayloadTelemetria();

        SensorTemperatura tempSensor = new SensorTemperatura(
                "temp-01", "Sala 1", conexao, calibrador, log, payload);

        tempSensor.calibrar();
        tempSensor.transmitirLeitura();

        SensorPressao pressSensor = new SensorPressao(
                "press-01", "Pátio", conexao, calibrador, log, payload);

        pressSensor.calibrar();
        pressSensor.transmitirLeitura();
    }
}
```

O código acima compila e demonstra a hierarquia completa, com as dependências em forma de stubs.  Basta substituir os stubs pelos componentes reais (biblioteca MQTT, lógica de calibração, etc.) quando for integrar