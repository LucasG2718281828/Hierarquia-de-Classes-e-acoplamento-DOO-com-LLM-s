package br.edu.ifsudestemg.doo.gemini.Fiscal;

import java.util.List;

public class NotaFiscalEletronica extends DocumentoFiscal {

    private String numero;
    private String serie;
    private String dataEmissao;

    public NotaFiscalEletronica(String numero,
                                String serie,
                                String dataEmissao,
                                Emitente emitente,
                                Destinatario destinatario,
                                List<ItemTributado> itens,
                                CertificadoDigital certificado,
                                RepositorioXml repositorio,
                                ImpostoCalculador calculador) {
        super(emitente, destinatario, itens, certificado, repositorio, calculador);
        this.numero = numero;
        this.serie = serie;
        this.dataEmissao = dataEmissao;
    }

    @Override
    public void validar() throws Exception {
        if (numero == null || numero.isEmpty()) {
            throw new IllegalArgumentException("Número da NF-e é obrigatório.");
        }
        if (serie == null || serie.isEmpty()) {
            throw new IllegalArgumentException("Série da NF-e é obrigatória.");
        }
        if (dataEmissao == null || dataEmissao.isEmpty()) {
            throw new IllegalArgumentException("Data de emissão é obrigatória.");
        }
        // Validações adicionais podem ser delegadas a um validador externo.
    }

    // Getters e Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }
}