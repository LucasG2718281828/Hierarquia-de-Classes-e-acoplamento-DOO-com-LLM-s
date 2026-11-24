**SensorIoT.java** – classe base (pai)

```java
package iot.sensors;

/**
 * Classe base que representa um sensor IoT.
 * <p>
 * Responsável por calibrar o sensor e transmitir leituras via MQTT.
 * </p>
 */
public abstract class SensorIoT {

    /* ------------------------------------------------------------------ */
    /*  Atributos comuns a todos os sensores                              */
    /* ------------------------------------------------------------------ */
    protected final String id;          // identificador único do sensor
    protected final String location;    // local onde o sensor está instalado

    /* ------------------------------------------------------------------ */
    /*  Dependências (injetadas via construtor)                           */
    /* ------------------------------------------------------------------ */
    protected final ConexaoMqtt conexaoMqtt;
    protected final Calibrador calibrador;
    protected final LogManager logManager;
    protected final PayloadTelemetria payloadTelemetria;

    /* ------------------------------------------------------------------ */
    /*  Construtor                                                        */
    /* ------------------------------------------------------------------ */
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

    /* ------------------------------------------------------------------ */
    /*  Métodos públicos                                                   */
    /* ------------------------------------------------------------------ */

    /**
     * Calibra o sensor chamando o {@link Calibrador}.
     */
    public void calibrar() {
        logManager.log("Calibrando sensor " + id + " (" + getClass().getSimpleName() + ")");
        calibrador.calibrarSensor(this);
        logManager.log("Sensor " + id + " calibrado com sucesso.");
    }

    /**
     * Transmite a leitura atual do sensor via MQTT.
     */
    public void transmitirLeitura() {
        String payload = payloadTelemetria.buildPayload(this);
        String topic = getMqttTopic();

        logManager.log("Transmitindo leitura do sensor " + id + " para o tópico " + topic);
        conexaoMqtt.publish(topic, payload);
        logManager.log("Leitura transmitida com sucesso.");
    }

    /* ------------------------------------------------------------------ */
    /*  Métodos auxiliares (pode ser sobrescrito)                         */
    /* ------------------------------------------------------------------ */

    /**
     * Retorna a leitura atual do sensor. Cada sub‑classe deve implementar
     * a lógica específica de aquisição de dados.
     *
     * @return valor numérico da leitura
     */
    protected abstract double obterLeitura();

    /**
     * Retorna o tópico MQTT que será usado para publicar a leitura.
     * Sub‑classes podem sobrescrever se quiserem usar um tópico diferente.
     *
     * @return tópico MQTT
     */
    protected String getMqttTopic() {
        return "sensors/" + getClass().getSimpleName().toLowerCase();
    }

    /* ------------------------------------------------------------------ */
    /*  Getters (opcional)                                                */
    /* ------------------------------------------------------------------ */

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }
}
```

---

**SensorTemperatura.java** – sub‑classe concreta

```java
package iot.sensors;

/**
 * Sensor de temperatura que herda de {@link SensorIoT}.
 */
public class SensorTemperatura extends SensorIoT {

    /* ------------------------------------------------------------------ */
    /*  Atributos específicos (exemplo)                                   */
    /* ------------------------------------------------------------------ */
    private double temperaturaAtual;   // última temperatura lida

    /* ------------------------------------------------------------------ */
    /*  Construtor                                                        */
    /* ------------------------------------------------------------------ */
    public SensorTemperatura(String id,
                             String location,
                             ConexaoMqtt conexaoMqtt,
                             Calibrador calibrador,
                             LogManager logManager,
                             PayloadTelemetria payloadTelemetria) {
        super(id, location, conexaoMqtt, calibrador, logManager, payloadTelemetria);
    }

    /* ------------------------------------------------------------------ */
    /*  Implementação do método abstrato                                  */
    /* ------------------------------------------------------------------ */
    @Override
    protected double obterLeitura() {
        // Simulação de leitura – em um cenário real, leria de um ADC ou API
        temperaturaAtual = 20.0 + Math.random() * 10.0;   // 20‑30°C
        return temperaturaAtual;
    }

    /* ------------------------------------------------------------------ */
    /*  Métodos auxiliares (opcional)                                     */
    /* ------------------------------------------------------------------ */

    public double getTemperaturaAtual() {
        return temperaturaAtual;
    }
}
```

---

**SensorPressao.java** – sub‑classe concreta

```java
package iot.sensors;

/**
 * Sensor de pressão que herda de {@link SensorIoT}.
 */
public class SensorPressao extends SensorIoT {

    /* ------------------------------------------------------------------ */
    /*  Atributos específicos (exemplo)                                   */
    /* ------------------------------------------------------------------ */
    private double pressaoAtual;   // última pressão lida

    /* ------------------------------------------------------------------ */
    /*  Construtor                                                        */
    /* ------------------------------------------------------------------ */
    public SensorPressao(String id,
                         String location,
                         ConexaoMqtt conexaoMqtt,
                         Calibrador calibrador,
                         LogManager logManager,
                         PayloadTelemetria payloadTelemetria) {
        super(id, location, conexaoMqtt, calibrador, logManager, payloadTelemetria);
    }

    /* ------------------------------------------------------------------ */
    /*  Implementação do método abstrato                                  */
    /* ------------------------------------------------------------------ */
    @Override
    protected double obterLeitura() {
        // Simulação de leitura – em um cenário real, leria de um sensor físico
        pressaoAtual = 1000.0 + Math.random() * 50.0;   // 1000‑1050 hPa
        return pressaoAtual;
    }

    /* ------------------------------------------------------------------ */
    /*  Métodos auxiliares (opcional)                                     */
    /* ------------------------------------------------------------------ */

    public double getPressaoAtual() {
        return pressaoAtual;
    }
}
```

---

**Dependências – stubs (estrutura mínima para compilação)**

```java
package iot.sensors;

/**
 * Stub da classe que representa a conexão MQTT.
 */
public class ConexaoMqtt {

    public void connect() {
        // Conexão simulada
    }

    public void publish(String topic, String payload) {
        // Publicação simulada
        System.out.println("[MQTT] Publicando em " + topic + ": " + payload);
    }
}
```

```java
package iot.sensors;

/**
 * Stub do calibrador de sensores.
 */
public class Calibrador {

    public void calibrarSensor(SensorIoT sensor) {
        // Calibração simulada
        System.out.println("[Calibrador] Calibrando sensor " + sensor.getId());
    }
}
```

```java
package iot.sensors;

/**
 * Stub do gerenciador de logs.
 */
public class LogManager {

    public void log(String message) {
        // Log simples
        System.out.println("[LOG] " + message);
    }
}
```

```java
package iot.sensors;

/**
 * Stub que cria o payload de telemetria a ser enviado via MQTT.
 */
public class PayloadTelemetria {

    public String buildPayload(SensorIoT sensor) {
        // Construção simples de JSON
        double leitura = sensor.obterLeitura();
        return String.format("{\"id\":\"%s\",\"location\":\"%s\",\"leitura\":%.2f}",
                sensor.getId(), sensor.getLocation(), leitura);
    }
}
```

---

