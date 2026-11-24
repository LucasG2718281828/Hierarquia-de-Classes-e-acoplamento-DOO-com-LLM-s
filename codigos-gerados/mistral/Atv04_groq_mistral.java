**Java – Hierarquia de contas bancárias**

```java
// ──────────────────────────────────────────────────────────────────────
// 1.  Dependências (stubs – apenas a estrutura mínima)
// ──────────────────────────────────────────────────────────────────────
package com.banco.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Titular da conta – apenas atributos e getters.
 */
public class Titular {
    private String nome;
    private String cpf;

    public Titular(String nome, String cpf) {
        this.nome = nome;
        this.cpf  = cpf;
    }

    public String getNome() { return nome; }
    public String getCpf()  { return cpf; }

    @Override
    public String toString() {
        return nome + " (" + cpf + ")";
    }
}

/**
 * Moeda – enum simples para ilustrar a dependência.
 */
public enum Moeda {
    BRL, USD, EUR
}

/**
 * Movimento – registro de uma operação no extrato.
 */
public class Movimento {
    private String descricao;
    private BigDecimal valor;

    public Movimento(String descricao, BigDecimal valor) {
        this.descricao = descricao;
        this.valor     = valor;
    }

    public String getDescricao() { return descricao; }
    public BigDecimal getValor() { return valor; }

    @Override
    public String toString() {
        return descricao + " : " + valor;
    }
}

/**
 * Extrato – lista de movimentos. Apenas os métodos necessários para
 * que o código compile.
 */
public class Extrato {
    private List<Movimento> movimentos = new ArrayList<>();

    public void adicionarMovimento(String descricao, BigDecimal valor) {
        movimentos.add(new Movimento(descricao, valor));
    }

    public List<Movimento> getMovimentos() {
        return movimentos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Movimento m : movimentos) {
            sb.append(m).append("\n");
        }
        return sb.toString();
    }
}
```

---

**2.  Classe Pai – `ContaBancaria`**

```java
package com.banco.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Classe abstrata que representa uma conta bancária genérica.
 */
public abstract class ContaBancaria {

    protected Titular titular;
    protected BigDecimal saldo;
    protected Extrato extrato;
    protected Moeda moeda;

    public ContaBancaria(Titular titular, BigDecimal saldoInicial, Moeda moeda) {
        this.titular = titular;
        this.saldo   = saldoInicial.setScale(2, RoundingMode.HALF_EVEN);
        this.moeda   = moeda;
        this.extrato = new Extrato();
    }

    /* ---------- Operações básicas ---------- */

    /**
     * Depósito simples – apenas soma o valor ao saldo e registra no extrato.
     */
    public void depositar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo.");
        }
        saldo = saldo.add(valor);
        extrato.adicionarMovimento("Depósito", valor);
    }

    /**
     * Saque genérico – subtrai o valor do saldo se houver fundos suficientes.
     * Subclasses podem sobrescrever para aplicar tarifas.
     */
    public void sacar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo.");
        }
        if (saldo.compareTo(valor) < 0) {
            throw new IllegalStateException("Saldo insuficiente.");
        }
        saldo = saldo.subtract(valor);
        extrato.adicionarMovimento("Saque", valor.negate());
    }

    /* ---------- Tarifa (pode ser sobrescrita) ---------- */

    /**
     * Calcula a tarifa a ser aplicada sobre o valor de saque.
     * Por padrão, não há tarifa.
     */
    protected BigDecimal calcularTarifa(BigDecimal valor) {
        return BigDecimal.ZERO;
    }

    /* ---------- Getters ---------- */

    public Titular getTitular() { return titular; }
    public BigDecimal getSaldo() { return saldo; }
    public Extrato getExtrato() { return extrato; }
    public Moeda getMoeda() { return moeda; }

    /* ---------- Representação ---------- */

    @Override
    public String toString() {
        return String.format(
            "Conta[%s] - Saldo: %s %s",
            titular, saldo, moeda
        );
    }
}
```

---

**3.  Subclasse – `ContaCorrente`**

```java
package com.banco.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Conta corrente – aplica tarifa de 1% sobre cada saque.
 */
public class ContaCorrente extends ContaBancaria {

    private static final BigDecimal TARIFA_PERCENTUAL = new BigDecimal("0.01");

    public ContaCorrente(Titular titular, BigDecimal saldoInicial, Moeda moeda) {
        super(titular, saldoInicial, moeda);
    }

    @Override
    protected BigDecimal calcularTarifa(BigDecimal valor) {
        return valor.multiply(TARIFA_PERCENTUAL).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public void sacar(BigDecimal valor) {
        BigDecimal tarifa = calcularTarifa(valor);
        BigDecimal valorComTarifa = valor.add(tarifa);

        // Verifica se há saldo suficiente para cobrir valor + tarifa
        if (saldo.compareTo(valorComTarifa) < 0) {
            throw new IllegalStateException("Saldo insuficiente para cobrir valor + tarifa.");
        }

        // Subtrai valor + tarifa
        saldo = saldo.subtract(valorComTarifa);

        // Registra no extrato: valor do saque e tarifa separadamente
        extrato.adicionarMovimento("Saque", valor.negate());
        extrato.adicionarMovimento("Tarifa Corrente", tarifa.negate());
    }

    @Override
    public String toString() {
        return "ContaCorrente: " + super.toString();
    }
}
```

---

**4.  Subclasse – `ContaPoupanca`**

```java
package com.banco.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Conta poupança – aplica tarifa de 0,5% sobre