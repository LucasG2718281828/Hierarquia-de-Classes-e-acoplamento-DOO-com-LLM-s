package br.edu.ifsudestemg.doo.groq.Fiscal;

import java.time.LocalDateTime;

public class NotaFiscalEletronica extends DocumentoFiscal {

    private String protocolo;
    private LocalDateTime dataEmissao;

    public NotaFiscalEletronica() {
        super();
    }

    public NotaFiscalEletronica(Emitente emitente, Destinatario destinatario, List<ItemTributado> itens,
                                CertificadoDigital certificadoDigital, RepositorioXml repositorioXml,
                                ImpostoCalculador impostoCalculador, String protocolo, LocalDateTime dataEmissao) {
        super(emitente, destinatario, itens, certificadoDigital, repositorioXml, impostoCalculador);
        this.protocolo = protocolo;
        this.dataEmissao = dataEmissao;
    }

    @Override
    public void validar() {
        super.validar();
        // Validações adicionais específicas da NF-e
    }

    @Override
    public void assinarDigitalmente() {
        super.assinarDigitalmente();
        // Assinatura digital específica da NF-e
    }

    @Override
    public void calcularAlíquotasImpostos() {
        super.calcularAlíquotasImpostos();
        // Cálculo de impostos específico da NF-e
    }

    // Getters and Setters
    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }
}