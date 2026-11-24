**Java – Hierarquia de veículos**

```java
// ------------------------------------------------------------
// 1. Dependências (stubs – apenas a estrutura necessária)
// ------------------------------------------------------------
public class Motor {
    private String tipo;          // ex.: "gasolina", "diesel", "eletrico"
    private double potencia;      // em cavalos ou kW

    public Motor() { }

    public Motor(String tipo, double potencia) {
        this.tipo = tipo;
        this.potencia = potencia;
    }

    // getters / setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getPotencia() { return potencia; }
    public void setPotencia(double potencia) { this.potencia = potencia; }

    // método que pode ser usado para calcular consumo
    public double getConsumoBase() {
        // stub – retorna um valor fictício
        return 0.0;
    }
}

public class Combustivel {
    private String tipo;          // ex.: "gasolina", "etanol", "gas"
    private double capacidade;    // em litros

    public Combustivel() { }

    public Combustivel(String tipo, double capacidade) {
        this.tipo = tipo;
        this.capacidade = capacidade;
    }

    // getters / setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getCapacidade() { return capacidade; }
    public void setCapacidade(double capacidade) { this.capacidade = capacidade; }
}

public class Placa {
    private String numero;        // ex.: "ABC-1234"

    public Placa() { }

    public Placa(String numero) {
        this.numero = numero;
    }

    // getters / setters
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
}

// ------------------------------------------------------------
// 2. Classe Pai – Veiculo
// ------------------------------------------------------------
public abstract class Veiculo {
    // Atributos herdados (chassi, motor, combustível, placa)
    protected String chassi;
    protected Motor motor;
    protected Combustivel combustivel;
    protected Placa placa;

    // --------------------------------------------------------
    // Construtor
    // --------------------------------------------------------
    public Veiculo(String chassi, Motor motor, Combustivel combustivel, Placa placa) {
        this.chassi = chassi;
        this.motor = motor;
        this.combustivel = combustivel;
        this.placa = placa;
    }

    // --------------------------------------------------------
    // Métodos de negócio
    // --------------------------------------------------------

    /**
     * Calcula o consumo médio de combustível (litros por 100 km).
     * O cálculo pode ser baseado na potência do motor e no tipo de combustível.
     * Este método pode ser sobrescrito pelas subclasses se necessário.
     */
    public double calcularConsumo() {
        // Exemplo simples: consumo base do motor + ajuste pelo tipo de combustível
        double consumoBase = motor.getConsumoBase();          // stub
        double ajuste = 0.0;

        if ("gasolina".equalsIgnoreCase(combustivel.getTipo())) {
            ajuste = 0.0;
        } else if ("etanol".equalsIgnoreCase(combustivel.getTipo())) {
            ajuste = 0.1;   // etanol costuma consumir mais
        } else if ("gas".equalsIgnoreCase(combustivel.getTipo())) {
            ajuste = -0.05; // gás costuma consumir menos
        }

        return consumoBase + ajuste;
    }

    /**
     * Simula a aceleração do veículo.
     * O valor retornado pode representar a aceleração em m/s².
     * Subclasses podem alterar o comportamento.
     */
    public double acelerar() {
        // Exemplo simples: aceleração proporcional à potência do motor
        double potencia = motor.getPotencia(); // em kW
        return potencia / 100.0;               // stub
    }

    // --------------------------------------------------------
    // Getters / Setters
    // --------------------------------------------------------
    public String getChassi() { return chassi; }
    public void setChassi(String chassi) { this.chassi = chassi; }

    public Motor getMotor() { return motor; }
    public void setMotor(Motor motor) { this.motor = motor; }

    public Combustivel getCombustivel() { return combustivel; }
    public void setCombustivel(Combustivel combustivel) { this.combustivel = combustivel; }

    public Placa getPlaca() { return placa; }
    public void setPlaca(Placa placa) { this.placa = placa; }

    // --------------------------------------------------------
    // Representação textual
    // --------------------------------------------------------
    @Override
    public String toString() {
        return String.format(
            "%s [chassi=%s, placa=%s, motor=%s, combustivel=%s]",
            this.getClass().getSimpleName(),
            chassi,
            placa.getNumero(),
            motor.getTipo() + " " + motor.getPotencia() + "kW",
            combustivel.getTipo() + " " + combustivel.getCapacidade() + "L"
        );
    }
}

// ------------------------------------------------------------
// 3. Subclasse – Carro
// ------------------------------------------------------------
public class Carro extends Veiculo {
    private int numeroPortas;   // ex.: 4, 5

    public Carro(String chassi, Motor motor, Combustivel combustivel, Placa placa, int numeroPortas) {
        super(chassi, motor, combustivel, placa);
        this.numeroPortas = numeroPortas;
    }

    public int getNumeroPortas() { return numeroPortas; }
    public void setNumeroPortas(int numeroPortas) { this.numeroPortas = numeroPortas; }

    // Carro pode ter um consumo ligeiramente menor que o padrão
    @Override
    public double calcularConsumo() {
        double consumoPadrao = super.calcularConsumo();
        // carros costumam ter aerodinâmica melhor que motos
        return consumoPadrao * 0.95;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", portas=%d", numeroPortas);
    }
}

// ------------------------------------------------------------
// 4. Subclasse – Moto
// ------------------------------------------------------------
public class Moto extends Veiculo {
    private boolean haCapa;   // se a moto possui capa

    public Moto(String chassi, Motor motor, Combustivel combustivel, Placa placa, boolean haCapa) {
        super(chassi, motor, combustivel, placa);
        this.haCapa = haCapa;
    }

    public boolean isHaCapa() { return haCapa; }
    public void setHaCapa(boolean haCapa) { this.haCapa = haCapa; }

    // Moto costuma consumir mais por ser mais leve e ter menor aerodinâmica
    @Override
    public double calcularConsumo() {
        double consumoPadrao = super.calcularConsumo();
        return consumoPadrao * 1.10;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", capa=%s", haCapa ? "sim" : "não");
    }
}
```

### Como usar

```java
public class Main {
    public static void main(String[] args) {
        Motor motorCarro = new Motor("gasolina", 150);
        Combustivel combustivelCarro = new Combustivel("gasolina", 50);
        Placa placaCarro = new Placa("ABC-1234");

        Carro carro = new Carro("CHASSI123", motorCarro, combustivelCarro, placaCarro, 4);
        System.out.println(carro);
        System.out.println("Consumo: " + carro.calcularConsumo() + " L/100km");
        System.out.println("Aceleração: " + carro.acelerar() + " m/s²");

        Motor motorMoto = new Motor("gasolina", 80);
        Combustivel combustivelMoto = new Combustivel("gasolina", 15);
        Placa placaMoto = new Placa("XYZ-9876");

        Moto moto = new Moto("CHASSI456", motorMoto, combustivelMoto, placaMoto, true);
        System.out.println(moto);
        System.out.println("Consumo: " + moto.calcularConsumo() + " L/100km");
        System.out.println("Aceleração: " + moto.acelerar() + " m/s²");
    }
}
```

O código acima compila e demonstra a hierarquia `Veiculo → Carro` e