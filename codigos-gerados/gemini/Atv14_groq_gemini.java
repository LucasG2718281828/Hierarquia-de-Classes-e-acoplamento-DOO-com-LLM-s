**Java – Hierarquia de simuladores financeiros**

```java
package com.example.finance;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/* ------------------------------------------------------------------ */
/* 1 – Classe pai: SimuladorFinanceiro                                 */
/* ------------------------------------------------------------------ */
public abstract class SimuladorFinanceiro {

    /* ------------------------------------------------------------------ */
    /* 1.1 – Dependências (injetadas via construtor)                     */
    /* ------------------------------------------------------------------ */
    protected IndiceBacen indiceBacen;
    protected CurvaJuros curvaJuros;
    protected TabelaRegressivaIr tabelaRegressivaIr;
    protected BenchmarkIbov benchmarkIbov;
    protected GeradorGrafico geradorGrafico;
    protected ModeloRiscoVar modeloRiscoVar;
    protected CarteiraAtivos carteiraAtivos;
    protected CambioWs cambioWs;

    public SimuladorFinanceiro(
            IndiceBacen indiceBacen,
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

    /* ------------------------------------------------------------------ */
    /* 1.2 – Método público que dispara a simulação                       */
    /* ------------------------------------------------------------------ */
    public ProjectionResult simulateProjection(double initialCapital, int years) {
        // 1.2.1 – Gera projeção estocástica
        List<Double> projectedValues = simulateStochasticProjection(initialCapital, years);

        // 1.2.2 – Calcula VaR (Value‑at‑Risk) sobre a série de retornos
        double var = modeloRiscoVar.calculateVaR(projectedValues, 0.95);

        // 1.2.3 – Gera gráfico (opcional)
        geradorGrafico.generateGraph(projectedValues, "Projeção Financeira");

        // 1.2.4 – Retorna resultado
        return new ProjectionResult(projectedValues, var);
    }

    /* ------------------------------------------------------------------ */
    /* 1.3 – Método protegido que implementa a lógica estocástica        */
    /* ------------------------------------------------------------------ */
    protected List<Double> simulateStochasticProjection(double initialCapital, int years) {
        List<Double> values = new ArrayList<>();
        double current = initialCapital;

        for (int i = 0; i < years; i++) {
            // 1.3.1 – Taxa de juros do período (curva de juros)
            double juros = curvaJuros.getTaxa(i + 1);

            // 1.3.2 – Inflação (índice BACEN)
            double inflacao = indiceBacen.getIndice();

            // 1.3.3 – Câmbio (para ativos internacionais)
            double cambio = cambioWs.getExchangeRate("USD");

            // 1.3.4 – Retorno esperado da carteira (pesos dos ativos)
            double retornoCarteira = 0.0;
            for (Map.Entry<String, Double> entry : carteiraAtivos.getAssetWeights().entrySet()) {
                // Simulação simplificada: retorno = taxa de juros + 10% do peso
                retornoCarteira += (juros + 0.10) * entry.getValue();
            }

            // 1.3.5 – Ajuste por inflação e câmbio
            double retornoReal = (retornoCarteira - inflacao) * cambio;

            // 1.3.6 – Atualiza capital
            current = current * (1 + retornoReal);
            values.add(current);
        }

        return values;
    }
}

/* ------------------------------------------------------------------ */
/* 2 – Classe filha: SimuladorInvestimentoAvancado                     */
/* ------------------------------------------------------------------ */
class SimuladorInvestimentoAvancado extends SimuladorFinanceiro {

    public SimuladorInvestimentoAvancado(
            IndiceBacen indiceBacen,
            CurvaJuros curvaJuros,
            TabelaRegressivaIr tabelaRegressivaIr,
            BenchmarkIbov benchmarkIbov,
            GeradorGrafico geradorGrafico,
            ModeloRiscoVar modeloRiscoVar,
            CarteiraAtivos carteiraAtivos,
            CambioWs cambioWs) {

        super(indiceBacen, curvaJuros, tabelaRegressivaIr, benchmarkIbov,
              geradorGrafico, modeloRiscoVar, carteiraAtivos, cambioWs);
    }

    @Override
    public ProjectionResult simulateProjection(double initialCapital, int years) {
        // 2.1 – Chama a lógica base
        ProjectionResult base = super.simulateProjection(initialCapital, years);

        // 2.2 – Ajusta o resultado com um fator de risco adicional
        double fatorRisco = 1.05; // Exemplo: 5% de aumento de risco
        double adjustedVaR = base.getVar() * fatorRisco;

        // 2.3 – Retorna resultado ajustado
        return new ProjectionResult(base.get