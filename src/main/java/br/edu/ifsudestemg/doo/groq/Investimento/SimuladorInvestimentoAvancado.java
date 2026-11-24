package br.edu.ifsudestemg.doo.groq.Investimento;

/**
 * Simulador avançado que adiciona funcionalidades extras à simulação.
 */
public class SimuladorInvestimentoAvancado extends SimuladorFinanceiro {

    public SimuladorInvestimentoAvancado(IndiceBacen indiceBacen,
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
    public void simularProjecaoEstocastica(int periodo, double taxaInflação, double taxaCambio) {
        // Chama a lógica base
        super.simularProjecaoEstocastica(periodo, taxaInflação, taxaCambio);

        // Adiciona lógica avançada (placeholder)
        System.out.println("Simulação avançada: aplicando estratégias de hedge.");
    }
}