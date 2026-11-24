package br.edu.ifsudestemg.doo.gemini.Investimento;

public class SimuladorInvestimentoAvancado extends SimuladorFinanceiro {

    private double fatorAlavancagem;

    public SimuladorInvestimentoAvancado(IndiceBacen indiceBacen,
                                         CurvaJuros curvaJuros,
                                         TabelaRegressivaIr tabelaRegressivaIr,
                                         BenchmarkIbov benchmarkIbov,
                                         GeradorGrafico geradorGrafico,
                                         ModeloRiscoVar modeloRiscoVar,
                                         CarteiraAtivos carteiraAtivos,
                                         CambioWs cambioWs,
                                         double fatorAlavancagem) {
        super(indiceBacen, curvaJuros, tabelaRegressivaIr, benchmarkIbov,
              geradorGrafico, modeloRiscoVar, carteiraAtivos, cambioWs);
        this.fatorAlavancagem = fatorAlavancagem;
    }

    @Override
    public void simularProjecao() {
        // Executa a simulação base
        super.simularProjecao();

        // Ajuste avançado: aplicar alavancagem ao retorno líquido
        double retornoLiquido = carteiraAtivos.calcularRetornoLiquidoComAlavancagem(fatorAlavancagem);
        double riscoVar = modeloRiscoVar.calcularVar(retornoLiquido);
        double desempenhoBenchmark = benchmarkIbov.obterDesempenho();

        // Gerar gráfico específico para investimento avançado
        geradorGrafico.gerarGrafico(retornoLiquido, desempenhoBenchmark, riscoVar);
    }

    public double getFatorAlavancagem() {
        return fatorAlavancagem;
    }

    public void setFatorAlavancagem(double fatorAlavancagem) {
        this.fatorAlavancagem = fatorAlavancagem;
    }
}