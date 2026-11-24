**DocumentoFiscal.java**

```java
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe base que representa um documento fiscal genérico.
 * Contém a lógica comum de validação, assinatura digital e cálculo de impostos.
 */
public abstract class DocumentoFiscal {

    /* ---------- Atributos ---------- */
    protected String numero;
    protected LocalDate dataEmissao;
    protected Emitente emitente;
    protected Destinatario destinatario;
    protected List<ItemTributado> itens = new ArrayList<>();
    protected CertificadoDigital certificadoDigital;
    protected RepositorioXml repositorioXml;
    protected ImpostoCalculador impostoCalculador;

    /* ---------- Construtor ---------- */
    public DocumentoFiscal(String numero,
                           LocalDate dataEmissao,
                           Emitente emitente,
                           Destinatario destinatario,
                           CertificadoDigital certificadoDigital,
                           RepositorioXml repositorioXml,
                           ImpostoCalculador impostoCalculador) {
        this.numero = numero;
        this.dataEmissao = dataEmissao;
        this.emitente = emitente;
        this.destinatario = destinatario;
        this.certificadoDigital = certificadoDigital;
        this.repositorioXml = repositorioXml;
        this.impostoCalculador = impostoCalculador;
    }

    /* ---------- Métodos ---------- */

    /**
     * Valida os campos obrigatórios do documento fiscal.
     *
     * @throws IllegalArgumentException se algum campo obrigatório estiver inválido
     */
    public void validar() {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("Número do documento não pode ser nulo ou vazio.");
        }
        if (dataEmissao == null) {
            throw new IllegalArgumentException("Data de emissão não pode ser nula.");
        }
        if (emitente == null) {
            throw new IllegalArgumentException("Emitente não pode ser nulo.");
        }
        if (destinatario == null) {
            throw new IllegalArgumentException("Destinatário não pode ser nulo.");
        }
        if (itens.isEmpty()) {
            throw new IllegalArgumentException("O documento deve conter pelo menos um item tributado.");
        }
    }

    /**
     * Assina digitalmente o documento fiscal usando o certificado digital fornecido.
     */
    public void assinarDigitalmente() {
        if (certificadoDigital == null) {
            throw new IllegalStateException("Certificado digital não configurado.");
        }
        certificadoDigital.assinar(this);
    }

    /**
     * Calcula a alíquota de impostos para cada item tributado.
     *
     * @return o valor total de impostos calculado
     */
    public BigDecimal calcularAlíquotasImpostos() {
        BigDecimal totalImposto = BigDecimal.ZERO;
        for (ItemTributado item : itens) {
            BigDecimal imposto = impostoCalculador.calcular(item);
            totalImposto = totalImposto.add(imposto);
        }
        return totalImposto;
    }

    /* ---------- Getters e Setters ---------- */

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

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
```

---

**NotaFiscalEletronica.java**

```java
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * Representa uma Nota Fiscal Eletrônica (NF-e).
 * Possui atributos específicos da NF-e além dos herdados de DocumentoFiscal.
 */
public class NotaFiscalEletronica extends DocumentoFiscal {

    /* ---------- Atributos específicos ---------- */
    private String ambiente;          // "homologação" ou "produção"
    private String versaoSchema;      // Versão do esquema XML
    private String chaveAcesso;       // Chave de acesso da NF-e

    /* ---------- Construtor ---------- */
    public NotaFiscalEletronica(String numero,
                                LocalDate dataEmissao,
                                Emitente emitente,
                                Destinatario destinatario,
                                CertificadoDigital certificadoDigital,
                                RepositorioXml repositorioXml,
                                ImpostoCalculador impostoCalculador,
                                String ambiente,
                                String versao