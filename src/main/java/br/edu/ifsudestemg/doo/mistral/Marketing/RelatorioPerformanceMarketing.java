package br.edu.ifsudestemg.doo.mistral.Marketing;

import java.util.List;
import java.util.Map;

public class RelatorioPerformanceMarketing extends RelatorioAnalitico {
    private String canalMarketing;
    private String campanhaId;
    private double cpc; // Custo por clique
    private double cpm; // Custo por mil impressões
    private double conversao; // Taxa de conversão

    public RelatorioPerformanceMarketing() {
        super();
        this.setNomeRelatorio("Relatório de Performance de Marketing");
    }

    public void consolidarPerformancePorCampanha(List<String> tenant