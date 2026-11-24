```java
/*======================================================================
 *  Classe Pai – MeioDePagamento
 *====================================================================*/
public abstract class MeioDePagamento {

    /* Dependências -----------------------------------------------------*/
    protected final GatewayPagamento gatewayPagamento;
    protected final ValidadorFraude validadorFraude;
    protected final Recibo recibo;
    protected final Notificacao notificacao;
    protected final Moeda moeda;

    /* Construtor -------------------------------------------------------*/
    public MeioDePagamento(GatewayPagamento gatewayPagamento,
                           ValidadorFraude validadorFraude,
                           Recibo recibo,
                           Notificacao notificacao,
                           Moeda moeda) {
        this.gatewayPagamento = gatewayPagamento;
        this.validadorFraude   = validadorFraude;
        this.recibo            = recibo;
        this.notificacao       = notificacao;
        this.moeda             = moeda;
    }

    /* Métodos ----------------------------------------------------------*/
    /**
     * Efetua o pagamento.  A implementação padrão realiza:
     *  1. Verificação antifraude
     *  2. Processamento via gateway
     *  3. Emissão de recibo
     *  4. Notificação ao cliente
     *
     * @param valor O valor a ser pago
     * @throws PaymentException Se a verificação antifraude falhar ou o gateway
     *                          indicar erro
     */
    public void efetuarPagamento(double valor) throws PaymentException {
        // 1. Verificação antifraude
        if (!validadorFraude.validar(valor, getMetodoPagamento())) {
            throw new PaymentException("Fraude detectada – pagamento cancelado.");
        }

        // 2. Processamento via gateway
        boolean sucesso = gatewayPagamento.processarPagamento(valor, moeda);
        if (!sucesso) {
            throw new PaymentException("Gateway de pagamento falhou.");
        }

        // 3. Emissão de recibo
        recibo.gerarRecibo(valor, getMetodoPagamento());

        // 4. Notificação
        notificacao.enviar("Pagamento de R$" + valor + " via " + getMetodoPagamento() + " concluído.");
    }

    /**
     * Retorna a descrição do método de pagamento (ex.: “Cartão de Crédito”,
     * “Pix”).
     */
    protected abstract String getMetodoPagamento();
}

/*======================================================================
 *  Classe Filha – CartaoCredito
 *====================================================================*/
public class CartaoCredito extends MeioDePagamento {

    /* Atributos específicos -------------------------------------------*/
    private final String numeroCartao;
    private final String cvv;
    private final String validade;   // formato MM/AAAA

    /* Construtor -------------------------------------------------------*/
    public CartaoCredito(String numeroCartao,
                         String cvv,
                         String validade,
                         GatewayPagamento gatewayPagamento,
                         ValidadorFraude validadorFraude,
                         Recibo recibo,
                         Notificacao notificacao,
                         Moeda moeda) {
        super(gatewayPagamento, validadorFraude, recibo, notificacao, moeda);
        this.numeroCartao = numeroCartao;
        this.cvv          = cvv;
        this.validade     = validade;
    }

    /* Implementação do método abstrato --------------------------------*/
    @Override
    protected String getMetodoPagamento() {
        return "Cartão de Crédito";
    }

    /* Métodos auxiliares (ex.: validação de dados) ---------------------*/
    public boolean validarDadosCartao() {
        // Simples validação de tamanho (exemplo)
        return numeroCartao != null && numeroCartao.length() == 16 &&
               cvv != null && cvv.length() == 3 &&
               validade != null && validade.matches("\\d{2}/\\d{4}");
    }
}

/*======================================================================
 *  Classe Filha – Pix
 *====================================================================*/
public class Pix extends MeioDePagamento {

    /* Atributos específicos -------------------------------------------*/
    private final String chavePix;
    private final String nomeTitular;

    /* Construtor -------------------------------------------------------*/
    public Pix(String chavePix,
               String nomeTitular,
               GatewayPagamento gatewayPagamento,
               ValidadorFraude validadorFraude,
               Recibo recibo,
               Notificacao notificacao,
               Moeda moeda) {
        super(gatewayPagamento, validadorFraude, recibo, notificacao, moeda);
        this.chavePix    = chavePix;
        this.nomeTitular = nomeTitular;
    }

    /* Implementação do método abstrato --------------------------------*/
    @Override
    protected String getMetodoPagamento() {
        return "Pix";
    }

    /* Métodos auxiliares (ex.: validação de chave) ---------------------*/
    public boolean validarChavePix() {
        // Exemplo simples: chave deve ter 11 dígitos ou 60 caracteres (CPF/CNPJ ou e‑mail)
        return chavePix != null &&
               (chavePix.matches("\\d{11}") || chavePix.matches("\\d{14}") || chavePix.contains("@"));
    }
}

/*======================================================================
 *  Dependências – Esqueleto (para compilação)
 *====================================================================*/
class GatewayPagamento {
    public boolean processarPagamento(double valor, Moeda moeda) {
        // Simulação de processamento – sempre retorna sucesso
        System.out.println("Processando pagamento de " + valor + " " + moeda);
        return true;
    }
}

class ValidadorFraude {
    public boolean validar(double valor, String metodo) {
        // Simulação de verificação antifraude – sempre retorna verdadeiro
        System.out.println("Validando fraude para " + metodo + " no valor " + valor);
        return true;
    }
}

class Recibo {
    public void gerarRecibo(double valor, String metodo) {
        System.out.println("Recibo gerado: " + metodo + " – R$" + valor);
    }
}

class Notificacao {
    public void enviar(String mensagem) {
        System.out.println("Notificação enviada: " + mensagem);
    }
}

enum Moeda {
    BRL, USD, EUR
}

/*======================================================================
 *  Exceção de pagamento
 *====================================================================*/
class PaymentException extends Exception {
    public PaymentException(String mensagem) {
        super(mensagem);
    }
}
```

**Como usar**

```java
public class Demo {
    public static void main(String[] args) {
        GatewayPagamento gateway = new GatewayPagamento();
        ValidadorFraude validador = new ValidadorFraude();
        Recibo recibo = new Recibo();
        Notificacao notificacao = new Notificacao();

        CartaoCredito cc = new CartaoCredito(
                "1234567812345678",
                "123",
                "12/2025",
                gateway,
                validador,
                recibo,
                notificacao,
                Moeda.BRL);

        Pix pix = new Pix(
                "12345678901",
                "João Silva",
                gateway,
                validador,
                recibo,
                notificacao,
                Moeda.BRL);

        try {
            cc.efetuarPagamento(150.00);
            pix.efetuarPagamento(200.00);
        } catch (PaymentException e) {
            e.printStackTrace();
        }
    }
}
```

O código acima compila e demonstra a hierarquia de pagamento com as dependências mínimas necessárias. As classes de dependência são apenas esqueletos, permitindo que você substitua por implement