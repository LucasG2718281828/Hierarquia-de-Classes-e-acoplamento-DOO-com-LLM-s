package br.edu.ifsudestemg.doo.groq.Investimento;

/**
 * Simulador especializado em previdência.
 */
public class SimuladorPrevidencia extends SimuladorFinanceiro {

    public SimuladorPrevidencia(IndiceBacen indiceBacen,
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
    public void simularProjecaoEstocastica(int