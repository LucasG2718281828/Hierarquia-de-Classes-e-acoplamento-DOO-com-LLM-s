```java
/* ==========================================================
 *  DocumentoFiscal.java
 *  Hierarquia principal – classe pai
 * ==========================================================
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que representa um documento fiscal genérico.
 * Contém a lógica comum de validação, assinatura digital e cálculo de impostos.
 */
public abstract class DocumentoFiscal {

    /* ---------- Atributos ---------- */
    protected String numero;                     // Número do documento
    protected LocalDate dataEmissao;              // Data de emissão
    protected Emitente emitente;                  // Emitente do documento
    protected Destinatario destinatario;          // Destinatário do documento
    protected List<ItemTributado> itens;          // Itens tributados
    protected CertificadoDigital certificadoDigital; // Certificado digital para assinatura
    protected RepositorioXml repositorioXml;      // Repositório para persistir XML
    protected ImpostoCalculador impostoCalculador; // Calculadora de impostos

    /* ---------- Construtor ---------- */
    public DocumentoFiscal(String numero, LocalDate dataEmissao,
                           Emitente emitente, Destinatario destinatario,
                           CertificadoDigital certificadoDigital,
                           RepositorioXml repositorioXml,
                           ImpostoCalculador impostoCalculador) {
        this.numero = numero;
        this.dataEmissao = dataEmissao;
        this.emitente = emitente;
        this.destinatario = destinatario;
        this.itens = new ArrayList<>();
        this.certificadoDigital = certificadoDigital;
        this.repositorioXml = repositorioXml;
        this.impostoCalculador = impostoCalculador;
    }

    /* ---------- Métodos ---------- */

    /**
     * Valida os campos obrigatórios do documento fiscal.
     * @throws IllegalArgumentException se algum campo obrigatório estiver inválido.
     */
    public void validar() {
        if (numero == null || numero.isBlank())
            throw new IllegalArgumentException("Número do documento não pode ser nulo ou vazio.");
        if (dataEmissao == null)
            throw new IllegalArgumentException("Data de emissão não pode ser nula.");
        if (emitente == null)
            throw new IllegalArgumentException("Emitente não pode ser nulo.");
        if (destinatario == null)
            throw new IllegalArgumentException("Destinatário não pode ser nulo.");
        if (itens.isEmpty())
            throw new IllegalArgumentException("O documento deve conter pelo menos um item.");
    }

    /**
     * Assina digitalmente o documento fiscal.
     * Utiliza o certificado digital fornecido.
     */
    public void assinarDigitalmente() {
        String xml = this.toString(); // Representação XML simplificada
        certificadoDigital.assinar(xml);
    }

    /**
     * Calcula a alíquota total de impostos sobre todos os itens.
     * @return soma dos impostos calculados.
     */
    public double calcularAlíquotasImpostos() {
        double totalImposto = 0.0;
        for (ItemTributado item : itens) {
            totalImposto += impostoCalculador.calcularImposto(item);
        }
        return totalImposto;
    }

    /* ---------- Getters / Setters ---------- */

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public LocalDate getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(LocalDate dataEmissao) { this.dataEmissao = dataEmissao; }

    public Emitente getEmitente() { return emitente; }
    public void setEmitente(Emitente emitente) { this.emitente = emitente; }

    public Destinatario getDestinatario() { return destinatario; }
    public void setDestinatario(Destinatario destinatario) { this.destinatario = destinatario; }

    public List<ItemTributado> getItens() { return itens; }
    public void adicionarItem(ItemTributado item) { this.itens.add(item); }

    public CertificadoDigital getCertificadoDigital() { return certificadoDigital; }
    public void setCertificadoDigital(CertificadoDigital certificadoDigital) {
        this.certificadoDigital = certificadoDigital;
    }

    public RepositorioXml getRepositorioXml() { return repositorioXml; }
    public void setRepositorioXml(RepositorioXml repositorioXml) {
        this.repositorioXml = repositorioXml;
    }

    public ImpostoCalculador getImpostoCalculador() { return impostoCalculador; }
    public void setImpostoCalculador(ImpostoCalculador impostoCalculador) {
        this.impostoCalculador = impostoCalculador;
    }

    /* ---------- Representação XML (simplificada) ---------- */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<DocumentoFiscal>");
        sb.append("<Numero>").append(numero).append("</Numero>");
        sb.append("<DataEmissao>").append(dataEmissao).append("</DataEmissao>");
        sb.append("<Emitente>").append(emitente).append("</Emitente>");
        sb.append("<Destinatario>").append(destinatario).append("</Destinatario>");
        sb.append("<Itens>");
        for (ItemTributado item : itens) {
            sb.append("<Item>").append(item).append("</Item>");
        }
        sb.append("</Itens>");
        sb.append("</DocumentoFiscal>");
        return sb.toString();
    }
}

/* ==========================================================
 *  NotaFiscalEletronica.java
 *  Classe filha que representa uma NF-e
 * ==========================================================
 */
import java.time.LocalDate;

/**
 * Representa uma Nota Fiscal Eletrônica (NF-e).
 */
public class NotaFiscalEletronica extends DocumentoFiscal {

    /* ---------- Atributos específicos ---------- */
    private String ambiente;          // "homologacao" ou "producao"
    private String versaoSchema;      // Versão do esquema XML

    /* ---------- Construtor ---------- */
    public NotaFiscalEletronica(String numero, LocalDate dataEmissao,
                                Emitente emitente, Destinatario destinatario,
                                CertificadoDigital certificadoDigital,
                                RepositorioXml repositorioXml,
                                ImpostoCalculador impostoCalculador,
                                String ambiente, String versaoSchema) {
        super(numero, dataEmissao, emitente, destinatario,
              certificadoDigital, repositorioXml, impostoCalculador);
        this.ambiente = ambiente;
        this.versaoSchema = versaoSchema;
    }

    /* ---------- Getters / Setters ---------- */
    public String getAmbiente() { return ambiente; }
    public void setAmbiente(String ambiente) { this.ambiente = ambiente; }

    public String getVersao