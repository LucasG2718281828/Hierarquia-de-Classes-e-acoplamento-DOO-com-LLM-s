package br.edu.ifsudestemg.doo.gemini.Investimento;

public abstract class SimuladorFinanceiro {

    protected IndiceBacen indiceBacen;
    protected CurvaJuros curvaJuros;
    protected TabelaRegressivaIr tabelaRegressivaIr;
    protected BenchmarkIbov benchmarkIbov;
    protected GeradorGrafico geradorGrafico;
    protected ModeloRiscoVar modeloRiscoVar;
    protected CarteiraAtivos carteiraAtivos;
    protected CambioWs cambioWs;

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

    /**
     * Executa a simulação de projeção estocástica considerando juros, inflação e câmbio.
     */
    public void simularProjecao() {
        // 1. Obter taxa de juros da curva
        double taxaJuros = curvaJuros.obterTaxaMedia();

        // 2. Obter índice de inflação (ex.: índice BACEN)
        double inflacao = indiceBacen.obterIndiceInflacao();

        // 3. Obter taxa de câmbio
        double taxaCambio = cambioWs.obterTaxaCambio();

        // 4. Calcular retorno esperado da carteira
        double retornoEsperado = carteiraAtivos.calcularRetornoEsperado(taxaJuros, inflacao, taxaCambio);

        // 5. Aplicar modelo de risco (VaR)
        double riscoVar = modeloRiscoVar.calcularVar(retornoEsperado);

        // 6. Ajustar por imposto de renda regressivo
        double retornoLiquido = tabelaRegressivaIr.aplicarImposto(retornoEsperado);

        // 7. Comparar com benchmark
        double desempenhoBenchmark = benchmarkIbov.obterDesempenho();

        // 8. Gerar relatório gráfico
        geradorGrafico.gerarGrafico(retornoLiquido, desempenhoBenchmark, riscoVar);
    }

    // Métodos auxiliares que podem ser sobrescritos pelas subclasses
    protected double obterTaxaJuros() {
        return curvaJuros.obterTaxaMedia();
    }

    protected double obterInflacao() {
        return indiceBacen.obterIndiceInflacao();
    }

    protected double obterTaxaCambio() {
        return cambioWs.obterTaxaCambio();
    }
}