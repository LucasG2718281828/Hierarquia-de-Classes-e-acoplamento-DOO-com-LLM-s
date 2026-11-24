**Veículo – hierarquia principal (com atributos, construtores e métodos completos)**  

```java
// Veiculo.java
public abstract class Veiculo {

    /* ---------- Atributos ---------- */
    protected String chassi;          // número de chassi
    protected Motor motor;            // motor do veículo
    protected Combustivel combustivel; // combustível
    protected Placa placa;            // placa

    /* ---------- Construtor ---------- */
    public Veiculo(String chassi, Motor motor, Combustivel combustivel, Placa placa) {
        this.chassi = chassi;
        this.motor = motor;
        this.combustivel = combustivel;
        this.placa = placa;
    }

    /* ---------- Getters / Setters ---------- */
    public String getChassi() { return chassi; }
    public void setChassi(String chassi) { this.chassi = chassi; }

    public Motor getMotor() { return motor; }
    public void setMotor(Motor motor) { this.motor = motor; }

    public Combustivel getCombustivel() { return combustivel; }
    public void setCombustivel(Combustivel combustivel) { this.combustivel = combustivel; }

    public Placa getPlaca() { return placa; }
    public void setPlaca(Placa placa) { this.placa = placa; }

    /* ---------- Métodos de negócio ---------- */

    /**
     * Calcula o consumo de combustível (litros) para uma distância dada.
     * @param distancia em quilômetros
     * @return litros consumidos
     */
    public double calcularConsumo(double distancia) {
        // Exemplo simples: consumo médio de 8 km/l
        double consumoMedio = 8.0; // km/l
        return distancia / consumoMedio;
    }

    /**
     * Acelera o veículo até a velocidade desejada.
     * @param velocidade em km/h
     */
    public void acelerar(double velocidade) {
        // Lógica genérica de aceleração
        System.out.printf("%s acelerando para %.1f km/h%n", this.getClass().getSimpleName(), velocidade);
    }

    @Override
    public String toString() {
        return String.format("%s[chassi=%s, placa=%s]", 
                this.getClass().getSimpleName(), chassi, placa.getNumero());
    }
}
```

---

**Classe filha – Carro**

```java
// Carro.java
public class Carro extends Veiculo {

    private int numeroPortas;   // número de portas do carro

    public Carro(String chassi, Motor motor, Combustivel combustivel, Placa placa, int numeroPortas) {
        super(chassi, motor, combustivel, placa);
        this.numeroPortas = numeroPortas;
    }

    public int getNumeroPortas() { return numeroPortas; }
    public void setNumeroPortas(int numeroPortas) { this.numeroPortas = numeroPortas; }

    /* Override de aceleração – pode incluir lógica específica de carro */
    @Override
    public void acelerar(double velocidade) {
        System.out.printf("Carro com %d portas acelerando para %.1f km/h%n", 
                numeroPortas, velocidade);
    }

    @Override
    public String toString() {
        return super.toString() + String.format("[portas=%d]", numeroPortas);
    }
}
```

---

**Classe filha – Moto**

```java
// Moto.java
public class Moto extends Veiculo {

    private boolean temCapa;   // se a moto possui capa

    public Moto(String chassi, Motor motor, Combustivel combustivel, Placa placa, boolean temCapa) {
        super(chassi, motor, combustivel, placa);
        this.temCapa = temCapa;
    }

    public boolean isTemCapa() { return temCapa; }
    public void setTemCapa(boolean temCapa) { this.temCapa = temCapa; }

    /* Override de aceleração – pode incluir lógica específica de moto */
    @Override
    public void acelerar(double velocidade) {
        System.out.printf("Moto%s acelerando para %.1f km/h%n", 
                temCapa ? " com capa" : "", velocidade);
    }

    @Override
    public String toString() {
        return super.toString() + String.format("[capa=%s]", temCapa ? "sim" : "não");
    }
}
```

---

**Stubs das dependências (estrutura mínima para compilação)**  

```java
// Motor.java
public class Motor {

    public Motor() { }

    public void ligar() {
        // implementação vazia – apenas para compilação
    }

    public void desligar() {
        // implementação vazia – apenas para compilação
    }
}
```

```java
// Combustivel.java
public class Combustivel {

    public Combustivel() { }

    public double getCapacidade() {
        // retorno fictício
        return 0.0;
    }
}
```

```java
// Placa.java
public class Placa {

    private String numero;

    public Placa(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
```

---

### Como usar

```java
public class Main {
    public static void main(String[] args) {
        Motor motor = new Motor();
        Combustivel combustivel = new Combustivel();
        Placa placaCarro = new Placa("ABC-1234");
        Placa placaMoto = new Placa("XYZ-9876");

        Carro carro = new Carro("CHASSI-CARRO-001", motor, combustivel, placaCarro, 4);
        Moto moto = new Moto("CHASSI-MOTO-001", motor, combustivel, placaMoto, true);

        System.out.println(carro);
        carro.acelerar(120);

        System.out.println(moto);
        moto.acelerar(90);

        double consumo = carro.calcularConsumo(160); // 160 km
        System.out.printf("Consumo estimado: %.2f litros%n", consumo);
    }
}
```

O código acima compila e demonstra a hierarquia completa (`Veiculo`, `Carro`, `Moto`) junto com os stubs das dependências (`Motor`, `Combustivel`, `Placa`).  
Qualquer extensão de lógica de negócio pode ser adicionada nas classes filhas ou na classe pai conforme necessário.