package br.edu.ifsudestemg.doo.mistral.Fiscal;

import java.time.LocalDateTime;

public class CupomFiscal extends DocumentoFiscal {

    private String numeroCupom;
    private LocalDateTime dataEmissao;

    public CupomFiscal() {
        super();
    }

    public CupomFiscal(Emitente emitente, Destinatario destinatario, List<ItemTributado> itens,
                       CertificadoDigital certificadoDigital, RepositorioXml repositorioXml,
                       ImpostoCalculador impostoCalculador, String numeroCupom, LocalDateTime dataEmissao) {
        super(emitente, destinatario, itens, certificadoDigital, repositorioXml, impostoCalculador);
        this.numeroCupom = numeroCupom;
        this.dataEmissao = dataEmissao;
    }

    @Override
    public void validar() {
        super.validar();
        // Validações adicionais específicas do cupom fiscal
    }

    @Override
    public void assinarDigitalmente() {
        super.assinarDigitalmente();
        // Assinatura digital específica do cupom fiscal
    }

    @Override
    public void calcularAlíquotasImpostos() {
        super.calcularAlíquotasImpostos();
        // Cálculo de impostos específico do cupom fiscal
    }

    // Getters and Setters
    public String getNumeroCupom() {
        return numeroCupom;
    }

    public void setNumeroCupom(String numeroCupom) {
        this.numeroCupom = numeroCupom;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }
}