**SensorIoT.java** – classe‑pai (com atributos, construtor e os métodos de calibrar e transmitir)

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
    protected String topic = "sensors/unknown";

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
     * @return valor numérico da leitura
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
 * Sensor de temperatura.  A leitura é simulada retornando um valor aleatório
 * entre -20°C e 50°C.  Em um cenário real, a leitura viria de um ADC ou
 * de uma API de hardware.
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
        // Simulação de leitura
        return -20 + Math.random() * 70; // -20°C … 50°C
    }
}
```

---

**SensorPressao.java** – subclasse que representa um sensor de pressão

```java
package iot.sensors;

/**
 * Sensor de pressão.  A leitura é simulada retornando um valor aleatório
 * entre 950 hPa e 1050 hPa.
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
        // Simulação de leitura
        return 950 + Math.random() * 100; // 950 hPa … 1050 hPa
    }
}
```

---

## Stubs das dependências (só estrutura mínima)

**ConexaoMqtt.java**

```java
package iot.sensors;

/**
 * Stub da conexão MQTT.  Em produção, essa classe encapsularia a
 * biblioteca cliente MQTT (ex.: Eclipse Paho).
 */
public class ConexaoMqtt {

    public void connect() {
        // Conexão com broker MQTT
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
 * Stub do serviço de calibração.  A lógica real pode envolver
 * ajustes de offset, ganho, etc.
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
 * Stub de gerenciador de logs.  Em produção, poderia delegar a
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
        // Exemplo simples de payload JSON
        double leitura = sensor.obterLeitura();
        return String.format("{\"id\":\"%s\",\"location\":\"%s\",\"value\":%.2f}",
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

        SensorTemperatura temp = new SensorTemperatura(
                "temp-01", "Sala 1", conexao, calibrador, log, payload);

        SensorPressao pressao = new SensorPressao(
                "press-01", "Pátio", conexao, calibrador, log, payload);

        temp.calibrar();
        temp.transmitirLeitura();

        pressao.calibrar();
        pressao.transmitirLeitura();
    }
}
```

O código acima compila e demonstra a hierarquia completa, com as dependências