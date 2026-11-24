package br.edu.ifsudestemg.doo.gemini.Fiscal;

import java.util.List;

public abstract class DocumentoFiscal {

    protected Emitente emitente;
    protected Destinatario destinatario;
    protected List<ItemTributado> itens;
    protected CertificadoDigital certificado;
    protected RepositorioXml repositorio;
    protected ImpostoCalculador calculador;

    public DocumentoFiscal(Emitente emitente,
                           Destinatario destinatario,
                           List<ItemTributado> itens,
                           CertificadoDigital certificado,
                           RepositorioXml repositorio,
                           ImpostoCalculador calculador) {
        this.emitente = emitente;
        this.destinatario = destinatario;
        this.itens = itens;
        this.certificado = certificado;
        this.repositorio = repositorio;
        this.calculador = calculador;
    }

    public abstract void validar() throws Exception;

    public void assinarDigitalmente() throws Exception {
        if (certificado == null) {
            throw new IllegalStateException("Certificado digital não configurado.");
        }
        certificado.assinar(this);
    }

    public void calcularAliquotasImpostosFiscais() {
        if (calculador != null) {
            calculador.calcular(this);
        }
    }

    // Getters e Setters
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

    public CertificadoDigital getCertificado() {
        return certificado;
    }

    public void setCertificado(CertificadoDigital certificado) {
        this.certificado = certificado;
    }

    public RepositorioXml getRepositorio() {
        return repositorio;
    }

    public void setRepositorio(RepositorioXml repositorio) {
        this.repositorio = repositorio;
    }

    public ImpostoCalculador getCalculador() {
        return calculador;
    }

    public void setCalculador(ImpostoCalculador calculador) {
        this.calculador = calculador;
    }
}