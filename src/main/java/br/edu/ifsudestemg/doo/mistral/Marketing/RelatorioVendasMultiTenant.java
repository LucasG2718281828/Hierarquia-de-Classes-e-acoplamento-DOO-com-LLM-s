package br.edu.ifsudestemg.doo.mistral.Marketing;

import java.util.List;
import java.util.Map;

public class RelatorioVendasMultiTenant extends RelatorioAnalitico {
    private String periodoInicio;
    private String periodoFim;
    private String moeda;

    public RelatorioVendasMultiTenant() {
        super();
        this.setNomeRelatorio("Relatório de Vendas Multi-Tenant");
    }

    public void consolidarVendasPorTenant(List<String> tenantIds) {
        logAudit.registrarAcao("Consolidação de vendas iniciada");
        
        // Chama o método do pai para consolidar métricas gerais
        super.consolidarMetricasMultiTenant(tenantIds);
        
        // Adiciona métricas específicas de vendas
        Map<String, Object> metricas = this.getMetricas();
        metricas.put("totalVendas", 0.0);
        metricas.put("quantidadePedidos", 0);
        metricas.put("ticketMedio", 0.0);
        
        logAudit.registrarAcao("Consolidação de vendas finalizada");
    }

    public byte[] exportarVendasFormatoEspecifico(String formato) {
        // Aplica template específico para vendas
        super.aplicarTemplate("template_vendas");
        
        // Exporta no formato solicitado
        return super.exportarMultiFormato(formato);
    }

    public void agendarRelatorioVendasDiario(String emailDestino) {
        // Agenda para rodar todo dia às 08:00
        super.agendarEnvioRelatorio(emailDestino, "pdf", "0 0 8 * * ?");
    }

    // Getters e Setters
    public String getPeriodoInicio() { return periodoInicio; }
    public void setPeriodoInicio(String periodoInicio) { this.periodoInicio = periodoInicio; }
    
    public String getPeriodoFim() { return periodoFim; }
    public void setPeriodoFim(String periodoFim) { this.periodoFim = periodoFim; }
    
    public String getMoeda() { return moeda; }
    public void setMoeda(String moeda) { this.moeda = moeda; }
}