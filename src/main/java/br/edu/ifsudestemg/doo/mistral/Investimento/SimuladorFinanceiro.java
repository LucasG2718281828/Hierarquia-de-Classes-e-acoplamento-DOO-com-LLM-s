package br.edu.ifsudestemg.doo.mistral.Investimento;

import br.edu.ifsudestemg.doo.financeiro.IndiceBacen;
import br.edu.ifsudestemg.doo.financeiro.CurvaJuros;
import br.edu.ifsudestemg.doo.financeiro.TabelaRegressivaIr;
import br.edu.ifsudestemg.doo.financeiro.BenchmarkIbov;
import br.edu.ifsudestemg.doo.financeiro.GeradorGrafico;
import br.edu.ifsudestemg.doo.financeiro.ModeloRiscoVar;
import br.edu.ifsudestemg.doo.financeiro.CarteiraAtivos;
import br.edu.ifsudestemg.doo.financeiro.CambioWs;

public class SimuladorFinanceiro {

    private IndiceBacen indiceBacen;
    private CurvaJuros curvaJuros;
    private TabelaRegressivaIr tabelaRegressivaIr;
    private BenchmarkIbov benchmarkIbov;
    private GeradorGrafico geradorGrafico;
    private ModeloRiscoVar modeloRiscoVar;
    private CarteiraAtivos carteiraAtivos;
    private CambioWs cambioWs;

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

    public void simularProjecaoEstocastica(double periodo, int passos, double taxaInflacao, double taxaCambio) {
        // Simulação de projeção estocástica usando curva de juros, inflação e câmbio
        double[] retornos = new double[passos];
        for (int i = 0; i < passos; i++) {
            double tempo = (periodo / passos) * i;
            double taxaJuros = curvaJuros.getTaxa(tempo);
            double retorno = taxaJuros + taxaInflacao + taxaCambio;
            retornos[i] = retorno;
        }
        carteiraAtivos.setRetornos(retornos);
        double var = modeloRiscoVar.calcularVaR(retornos);
        System.out.println("Valor VaR calculado: " + var);
    }

    public void gerarRelatorio() {
        double[] retornos = carteiraAtivos.getRetornos();
        geradorGrafico.gerarGrafico("Relatório de Retornos", retornos);
        double var = modeloRiscoVar.calcularVaR(retornos);
        System.out.println("Relatório gerado. VaR: " + var);
    }

    public void atualizarIndicadores() {
        double taxaBase = indiceBacen.getTaxaBase();
        double taxaIbov = benchmarkIbov.getIndice();
        System.out.println("Atualizando indicadores: Taxa Base = " + taxaBase + ", Índice Ibovespa = " + taxaIbov);
    }

    // Getters e Setters
    public IndiceBacen getIndiceBacen() {
        return indiceBacen;
    }

    public void setIndiceBacen(IndiceBacen indiceBacen) {
        this.indiceBacen = indiceBacen;
    }

    public CurvaJuros getCurvaJuros() {
        return curvaJuros;
    }

    public void setCurvaJuros(CurvaJuros curvaJuros) {
        this.curvaJuros = curvaJuros;
    }

    public TabelaRegressivaIr getTabelaRegressivaIr() {
        return tabelaRegressivaIr;
    }

    public void setTabelaRegressivaIr(TabelaRegressivaIr tabelaRegressivaIr) {
        this.tabelaRegressivaIr = tabelaRegressivaIr;
    }

    public BenchmarkIbov getBenchmarkIbov() {
        return benchmarkIbov;
    }

    public void setBenchmarkIbov(BenchmarkIbov benchmarkIbov) {
        this.benchmarkIbov = benchmarkIbov;
    }

    public GeradorGrafico getGeradorGrafico() {
        return geradorGrafico;
    }

    public void setGeradorGrafico(GeradorGrafico geradorGrafico) {
        this.geradorGrafico = geradorGrafico;
    }

    public ModeloRiscoVar getModeloRiscoVar() {
        return modeloRiscoVar;
    }

    public void setModeloRiscoVar(ModeloRiscoVar modeloRiscoVar) {
        this.modeloRiscoVar = modeloRiscoVar;
    }

    public CarteiraAtivos getCarteiraAtivos() {
        return carteiraAtivos;
    }

    public void setCarteiraAtivos(CarteiraAtivos carteiraAtivos) {
        this.carteiraAtivos = carteiraAtivos;
    }

    public CambioWs getCambioWs() {
        return cambioWs;
    }

    public void setCambioWs(CambioWs cambioWs) {
        this.cambioWs = cambioWs;
    }
}