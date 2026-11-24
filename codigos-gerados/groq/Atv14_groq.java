**Java – Hierarquia de simuladores financeiros**

```java
// ---------------------------------------------------------------------------
// 1 – Dependências (stubs)
// ---------------------------------------------------------------------------

/**
 * Índice do Banco Central (BACEN)
 */
public class IndiceBacen {
    public double getIndice() { return 0.0; }
}

/**
 * Curva de juros (taxa de juros em função do vencimento)
 */
public class CurvaJuros {
    public double getTaxa(double vencimento) { return 0.0; }
}

/**
 * Tabela regressiva de imposto de renda
 */
public class TabelaRegressivaIr {
    public double getTaxaRetorno(double anos) { return 0.0; }
}

/**
 * Benchmark Ibovespa
 */
public class BenchmarkIbov {
    public double getBenchmarkReturn(int anos) { return 0.0; }
}

/**
 * Gerador de gráficos
 */
public class GeradorGrafico {
    public void generateGraph(String titulo, double[] dados) { /* vazio */ }
}

/**
 * Modelo de risco – cálculo de VaR
 */
public class ModeloRiscoVar {
    public double calculateVaR(double[] retornos, double confidenceLevel) { return 0.0; }
}

/**
 * Carteira de ativos – retorno esperado e risco
 */
public class CarteiraAtivos {
    public double getExpectedReturn() { return 0.0; }
    public double getRisk() { return 0.0; }
}

/**
 * Web‑service de câmbio
 */
public class CambioWs {
    public double getExchangeRate(String moeda) { return 1.0; }
}

/**
 * Resultado da projeção
 */
public class ProjectionResult {
    private double capitalFinal;
    private double[] retornosAnuais;

    public ProjectionResult(double capitalFinal, double[] retornosAnuais) {
        this.capitalFinal = capitalFinal;
        this.retornoAnuais = retornosAnuais;
    }

    public double getCapitalFinal() { return capitalFinal; }
    public double[] getRetornosAnuais() { return retornosAnuais; }
}


// ---------------------------------------------------------------------------
// 2 – Classe pai: SimuladorFinanceiro
// ---------------------------------------------------------------------------

/**
 * Classe abstrata que contém a lógica comum de projeção estocástica.
 */
public abstract class SimuladorFinanceiro {

    /* Dependências */
    protected IndiceBacen indiceBacen;
    protected CurvaJuros curvaJuros;
    protected TabelaRegressivaIr tabelaRegressivaIr;
    protected BenchmarkIbov benchmarkIbov;
    protected GeradorGrafico geradorGrafico;
    protected ModeloRiscoVar modeloRiscoVar;
    protected CarteiraAtivos carteiraAtivos;
    protected CambioWs cambioWs;

    /* Construtor */
    public SimuladorFinanceiro(IndiceBacen indiceBacen,
                               CurvaJuros curvaJuros,
                               TabelaRegressivaIr tabelaRegressivaIr,
                               BenchmarkIbov benchmarkIbov,
                               GeradorGrafico geradorGrafico,
                               ModeloRiscoVar modeloRiscoVar,
                               CarteiraAtivos carteiraAtivos,
                               CambioWs cambioWs) {
        this.indiceBacen = indiceBacen;
        this.curvaJuros = curvaJuros;
        this.tabelaRegressivaIr = tabelaRegressivaIr;
        this.benchmarkIbov = benchmarkIbov;
        this.geradorGrafico = geradorGrafico;
        this.modeloRiscoVar = modeloRiscoVar;
        this.carteiraAtivos = carteiraAtivos;
        this.cambioWs = cambioWs;
    }

    /* --------------------------------------------------------------------
     *  Método público que inicia a simulação
     * -------------------------------------------------------------------- */
    public ProjectionResult simulateProjection(double capitalInicial, int anos) {
        return simulateStochasticProjection(capitalInicial, anos);
    }

    /* --------------------------------------------------------------------
     *  Lógica comum de projeção estocástica
     * -------------------------------------------------------------------- */
    protected ProjectionResult simulateStochasticProjection(double capitalInicial, int anos) {
        double[] retornosAnuais = new double[anos];
        double capital = capitalInicial;

        for (int i = 0; i < anos; i++) {
            // 1. Taxa de juros do vencimento atual
            double taxaJuros = curvaJuros.getTaxa(i + 1);

            // 2. Inflação (simulada como taxa aleatória entre 1% e 3%)
            double inflacao = 0.01 + Math.random() * 0.02;

            // 3. Câmbio (simulado como taxa aleatória entre 1,0 e 1,2)
            double cambio = 1.0 + Math.random() * 0.2;

            // 4. Retorno da carteira (simplificado)
            double retornoCarteira = carteiraAtivos.getExpectedReturn()
                    + (Math.random() - 0.5) * carteiraAtivos.getRisk();

            // 5. Ajuste pelo imposto de renda
            double taxaIr = tabelaRegressivaIr.getTaxaRetorno(anos - i);
            double retornoLiquido = retornoCarteira * (1 - taxaIr);

            // 6. Calcular retorno anual
            double retornoAnual = taxaJuros + inflacao + retornoLiquido;

            // 7. Atualizar capital
            capital *= (1 + retornoAnual);

            // 8. Salvar retorno
            retorn