**Java – Hierarquia de Pagamento**

```java
// ---------------------------------------------------------------------------
// 1. Classe Pai – MeioDePagamento
// ---------------------------------------------------------------------------

/**
 * Classe abstrata que representa um meio de pagamento genérico.
 * Ela encapsula a lógica de verificação antifraude, processamento
 * via gateway, emissão de recibo e envio de notificação.
 */
public abstract class MeioDePagamento {

    /* -----------------------------------------------------------------------
     * Atributos comuns a todos os meios de pagamento
     * ----------------------------------------------------------------------- */
    protected double valor;                     // valor da transação
    protected Moeda moeda;                      // moeda da transação
    protected GatewayPagamento gateway;         // gateway de pagamento
    protected ValidadorFraude validadorFraude;   // serviço antifraude
    protected Recibo recibo;                    // serviço de recibo
    protected Notificacao notificacao;          // serviço de notificação

    /* -----------------------------------------------------------------------
     * Construtor
     * ----------------------------------------------------------------------- */
    public MeioDePagamento(double valor,
                           Moeda moeda,
                           GatewayPagamento gateway,
                           ValidadorFraude validadorFraude,
                           Recibo recibo,
                           Notificacao notificacao) {
        this.valor = valor;
        this.moeda = moeda;
        this.gateway = gateway;
        this.validadorFraude = validadorFraude;
        this.recibo = recibo;
        this.notificacao = notificacao;
    }

    /* -----------------------------------------------------------------------
     * Método público que executa a transação
     * ----------------------------------------------------------------------- */
    public void efetuarPagamento() {
        // 1. Verifica se a transação não é fraudulenta
        if (validadorFraude.verificarFraude(valor, moeda)) {
            // 2. Processa a transação no gateway
            gateway.processarPagamento(this);

            // 3. Emite o recibo
            emitirRecibo();

            // 4. Notifica o cliente
            notificacao.enviar("Pagamento efetuado com sucesso.");
        } else {
            // Caso a transação seja suspeita
            notificacao.enviar("Pagamento bloqueado por suspeita de fraude.");
        }
    }

    /* -----------------------------------------------------------------------
     * Método abstrato que deve ser implementado pelas subclasses
     * para gerar o recibo específico do meio de pagamento
     * ----------------------------------------------------------------------- */
    protected abstract void emitirRecibo();
}
```

```java
// ---------------------------------------------------------------------------
// 2. Classe Filha – Cartão de Crédito
// ---------------------------------------------------------------------------

/**
 * Representa um pagamento via cartão de crédito.
 */
public class CartaoCredito extends MeioDePagamento {

    /* -----------------------------------------------------------------------
     * Dados específicos do cartão
     * ----------------------------------------------------------------------- */
    private String numeroCartao;
    private String cvv;
    private String dataValidade;

    /* -----------------------------------------------------------------------
     * Construtor
     * ----------------------------------------------------------------------- */
    public CartaoCredito(double valor,
                         Moeda moeda,
                         GatewayPagamento gateway,
                         ValidadorFraude validadorFraude,
                         Recibo recibo,
                         Notificacao notificacao,
                         String numeroCartao,
                         String cvv,
                         String dataValidade) {
        super(valor, moeda, gateway, validadorFraude, recibo, notificacao);
        this.numeroCartao = numeroCartao;
        this.cvv = cvv;
        this.dataValidade = dataValidade;
    }

    /* -----------------------------------------------------------------------
     * Implementação do recibo específico para cartão de crédito
     * ----------------------------------------------------------------------- */
    @Override
    protected void emitirRecibo() {
        recibo.gerarRecibo("Cartão de Crédito",
                           valor,
                           moeda,
                           numeroCartao);
    }

    /* -----------------------------------------------------------------------
     * Getters (opcional)
     * ----------------------------------------------------------------------- */
    public String getNumeroCartao() { return numeroCartao; }
    public String getCvv() { return cvv; }
    public String getDataValidade() { return dataValidade; }
}
```

```java
// ---------------------------------------------------------------------------
// 3. Classe Filha – Pix
// ---------------------------------------------------------------------------

/**
 * Representa um pagamento via Pix.
 */
public class Pix extends MeioDePagamento {

    /* -----------------------------------------------------------------------
     * Dados específicos do Pix
     * ----------------------------------------------------------------------- */
    private String chavePix;

    /* -----------------------------------------------------------------------
     * Construtor
     * ----------------------------------------------------------------------- */
    public Pix(double valor,
               Moeda moeda,
               GatewayPagamento gateway,
               ValidadorFraude validadorFraude,
               Recibo recibo,
               Notificacao notificacao,
               String chavePix) {
        super(valor, moeda, gateway, validadorFraude, recibo, notificacao);
        this.chavePix = chavePix;
    }

    /* -----------------------------------------------------------------------
     * Implementação do recibo específico para Pix
     * ----------------------------------------------------------------------- */
    @Override
    protected void emitirRecibo() {
        recibo.gerarRecibo("Pix",
                           valor,
                           moeda,
                           chavePix);
    }

    /* -----------------------------------------------------------------------
     * Getter (opcional)
     * ----------------------------------------------------------------------- */
    public String getChavePix() { return chavePix; }
}
```

```java
// ---------------------------------------------------------------------------
// 4. Dependências – Esqueleto (stubs)
// ---------------------------------------------------------------------------

/**
 * Gateway de pagamento – stub para compilar.
 */
public class GatewayPagamento {
    public void processarPagamento(MeioDePagamento pagamento) {
        // Implementação real seria a chamada ao provedor de pagamento
    }
}

/**
 * Validador antifraude – stub para compilar.
 */
public class ValidadorFraude {
    public boolean verificarFraude(double valor, Moeda moeda) {
