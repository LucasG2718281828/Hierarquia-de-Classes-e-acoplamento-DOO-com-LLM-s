Aqui está a implementação completa em Java, contendo a hierarquia principal implementada em detalhes e os *stubs* (esqueletos) necessários para as dependências compilarem.

### 1. Dependências (Stubs / Esqueletos)

```java
// Stub da classe Titular
class Titular {
    private String nome;
    private String cpf;

    public Titular(String nome, String cpf) {}
}

// Stub da classe Moeda
class Moeda {
    private String codigo; // Ex: "BRL", "USD"

    public Moeda(String codigo) {}
}

// Stub da classe Extrato
class Extrato {
    public void registrarTransacao(String descricao, double valor) {
        // Método vazio (stub)
    }

    public void exibirExtrato() {
        // Método vazio (stub)
    }
}
```

---

### 2. Classe Pai: `ContaBancaria`

```java
public abstract class ContaBancaria {
    protected String numeroConta;
    protected double saldo;
    protected Titular titular;
    protected Moeda moeda;
    protected Extrato extrato;

    public ContaBancaria(String numeroConta, Titular titular, Moeda moeda) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.moeda = moeda;
        this.saldo = 0.0;
        this.extrato = new Extrato();
    }

    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            this.extrato.registrarTransacao("Depósito", valor);
        } else {
            System.out.println("O valor de depósito deve ser positivo.");
        }
    }

    // Método base de saque que pode ser sobrescrito pelas subclasses
    public boolean sacar(double valor) {
        if (valor > 0 && this.saldo >= valor) {
            this.saldo -= valor;
            this.extrato.registrarTransacao("Saque Comum", valor);
            return true;
        }
        System.out.println("Saldo insuficiente ou valor inválido.");
        return false;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public Titular getTitular() {
        return titular;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public Extrato getExtrato() {
        return extrato;
    }
}
```

---

### 3. Subclasses: `ContaCorrente` e `ContaPoupanca`

```java
// Subclasse ContaCorrente
public class ContaCorrente extends ContaBancaria {
    // Tarifa fixa por operação de saque em Conta Corrente
    private double tarifaSaque;

    public ContaCorrente(String numeroConta, Titular titular, Moeda moeda, double tarifaSaque) {
        super(numeroConta, titular, moeda);
        this.tarifaSaque = tarifaSaque;
    }

    @Override
    public boolean sacar(double valor) {
        double valorTotal = valor + this.tarifaSaque;

        // Valida se o saldo cobre o saque + a tarifa bancária específica
        if (valor > 0 && this.saldo >= valorTotal) {
            this.saldo -= valorTotal;
            this.extrato.registrarTransacao("Saque Conta Corrente", valor);
            this.extrato.registrarTransacao("Tarifa de Saque (CC)", this.tarifaSaque);
            return true;
        }

        System.out.println("Saldo insuficiente para cobrir o saque e a tarifa de R$ " + this.tarifaSaque);
        return false;
    }

    public double getTarifaSaque() {
        return tarifaSaque;
    }

    public void setTarifaSaque(double tarifaSaque) {
        this.tarifaSaque = tarifaSaque;
    }
}
```

```java
// Subclasse ContaPoupanca
public class ContaPoupanca extends ContaBancaria {
    // Tarifa percentual sobre o valor sacado (ex: 0.01 para 1%)
    private double percentualTarifaSaque;

    public ContaPoupanca(String numeroConta, Titular titular, Moeda moeda, double percentualTarifaSaque) {
        super(numeroConta, titular, moeda);
        this.percentualTarifaSaque = percentualTarifaSaque;
    }

    @Override
    public boolean sacar(double valor) {
        double tarifaCalculada = valor * this.percentualTarifaSaque;
        double valorTotal = valor + tarifaCalculada;

        // Valida se o saldo cobre o valor + tarifa percentual da Poupança
        if (valor > 0 && this.saldo >= valorTotal) {
            this.saldo -= valorTotal;
            this.extrato.registrarTransacao("Saque Conta Poupança", valor);
            this.extrato.registrarTransacao("Tarifa Percentual (CP)", tarifaCalculada);
            return true;
        }

        System.out.println("Saldo insuficiente para o saque com a tarifa proporcional aplicada.");
        return false;
    }

    public double getPercentualTarifaSaque() {
        return percentualTarifaSaque;
    }

    public void setPercentualTarifaSaque(double percentualTarifaSaque) {
        this.percentualTarifaSaque = percentualTarifaSaque;
    }
}
```