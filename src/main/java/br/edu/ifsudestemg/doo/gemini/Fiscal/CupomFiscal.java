package br.edu.ifsudestemg.doo.gemini.Fiscal;

import java.util.List;

public class CupomFiscal extends DocumentoFiscal {

    private String numeroCaixa;
    private String dataEmissao;

    public CupomFiscal(String numeroCaixa,
                       String dataEmissao,
                       Emitente emitente,
                       Destinatario destinatario,
                       List<ItemTributado> itens,
                       CertificadoDigital certificado,
                       RepositorioXml repositorio,
                       ImpostoCalculador calculador) {
        super(emitente, destinatario, itens, certificado, repositorio, calculador);
        this.numeroCaixa = numeroCaixa;
        this.dataEmissao = dataEmissao;
    }

    @Override
    public void validar() throws Exception {
        if (numeroCaixa == null || numeroCaixa.isEmpty()) {
            throw new IllegalArgumentException("Número do caixa é obrigatório.");
        }
        if (dataEmissao == null || dataEmissao.isEmpty()) {
            throw new IllegalArgumentException("Data de emissão é obrigatória.");
        }
        // Outras regras de negócio específicas para cupom podem ser inseridas aqui.
    }

    // Getters e Setters
    public String getNumeroCaixa() {
        return numeroCaixa;
    }

    public void setNumeroCaixa(String numeroCaixa) {
        this.numeroCaixa = numeroCaixa;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }
}