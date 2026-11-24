package br.edu.ifsudestemg.doo.gemini.Investimento;

public class SimuladorPrevidencia extends SimuladorFinanceiro {

    private int anosContribuicao;
    private double taxaRetirada;

    public SimuladorPrevidencia(IndiceBacen indiceBacen,
                                CurvaJuros curvaJuros,
                                TabelaRegressivaIr tabelaRegressivaIr,
                                BenchmarkIbov benchmarkIbov,
                                GeradorGrafico geradorGrafico,
                                ModeloRiscoVar modeloRiscoVar,
                                CarteiraAtivos carteiraAtivos,
                                CambioWs cambioWs,
                                int anosContribuicao,
                                double taxaRetirada) {
        super(indiceBacen, curvaJuros, tabelaRegressivaIr, benchmarkIbov,
              geradorGrafico, modeloRiscoVar, carteiraAtivos, cambioWs);
        this.anosContribuicao = anosContribuicao;
        this.taxaRetirada = taxaRetirada;
    }

    @Override
    public void simularProjecao() {
        // Simulação base
        super.simularProjecao();

        // Cálculo específico de previdência
        double saldoAcumulado = carteiraAtivos.calcularSaldoAcumulado(anosContribuicao);
        double rendaMensal = saldoAcumulado * taxaRetirada;

        // Gerar relatório gráfico para previdência
        geradorGrafico.gerarGraficoPrevidencia(saldoAcumulado, rendaMensal);
    }

    public int getAnosContribuicao() {
        return anosContribuicao;
    }

    public void setAnosContribuicao(int anosContribuicao) {
        this.anosContribuicao = anosContribuicao;
    }

    public double getTaxaRetirada() {
        return taxaRetirada;
    }

    public void setTaxaRetirada(double taxaRetirada) {
        this.taxaRetirada = taxaRetirada;
    }
}