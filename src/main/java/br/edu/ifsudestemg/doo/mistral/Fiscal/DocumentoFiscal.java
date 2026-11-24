package br.edu.ifsudestemg.doo.mistral.Fiscal;

import java.util.List;

public abstract class DocumentoFiscal {

    protected Emitente emitente;
    protected Destinatario destinatario;
    protected List<ItemTributado> itens;
    protected CertificadoDigital certificadoDigital;
    protected RepositorioXml repositorioXml;
    protected ImpostoCalculador impostoCalculador;

    public DocumentoFiscal() {
    }

    public DocumentoFiscal(Emitente emitente, Destinatario destinatario, List<ItemTributado> itens,
                           CertificadoDigital certificadoDigital, RepositorioXml repositorioXml,
                           ImpostoCalculador impostoCalculador) {
        this.emitente = emitente;
        this.destinatario = destinatario;
        this.itens = itens;
        this.certificadoDigital = certificadoDigital;
        this.repositorioXml = repositorioXml;
        this.impostoCalculador = impostoCalculador;
    }

    public void validar() {
        // Implementar validações específicas do documento fiscal
    }

    public void assinarDigitalmente() {
        // Implementar assinatura digital usando CertificadoDigital
    }

    public void calcularAlíquotasImpostos() {
        // Implementar cálculo de impostos usando ImpostoCalculador
    }

    // Getters and Setters
    public Emitente getEmitente() {
        return emitente;
    }

    public void setEmitente(Emitente emitente) {
        this.emitente = emitente;
    }

    public Destinatario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Destinatario destinatario) {
        this.destinatario = destinatario;
    }

    public List<ItemTributado> getItens() {
        return itens;
    }

    public void setItens(List<ItemTributado> itens) {
        this.itens = itens;
    }

    public CertificadoDigital getCertificadoDigital() {
        return certificadoDigital;
    }

    public void setCertificadoDigital(CertificadoDigital certificadoDigital) {
        this.certificadoDigital = certificadoDigital;
    }

    public RepositorioXml getRepositorioXml() {
        return repositorioXml;
    }

    public void setRepositorioXml(RepositorioXml repositorioXml) {
        this.repositorioXml = repositorioXml;
    }

    public ImpostoCalculador getImpostoCalculador() {
        return impostoCalculador;
    }

    public void setImpostoCalculador(ImpostoCalculador impostoCalculador) {
        this.impostoCalculador = impostoCalculador;
    }
}