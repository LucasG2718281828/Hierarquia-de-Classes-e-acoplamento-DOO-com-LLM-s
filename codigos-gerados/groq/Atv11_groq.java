```java
/* ==========================================================
 *  ServicoEntrega.java
 * ==========================================================
 */
public class ServicoEntrega {

    /* Dependências */
    protected RoteirizadorGps roteirizadorGps;
    protected Rastreador rastreador;
    protected SeguroCarga seguroCarga;
    protected Transportadora transportadora;
    protected TabelaFrete tabelaFrete;
    protected NotificadorSms notificadorSms;
    protected Aduana aduana;
    protected CalculadorPrazo calculadorPrazo;

    /* Construtor */
    public ServicoEntrega(RoteirizadorGps roteirizadorGps,
                          Rastreador rastreador,
                          SeguroCarga seguroCarga,
                          Transportadora transportadora,
                          TabelaFrete tabelaFrete,
                          NotificadorSms notificadorSms,
                          Aduana aduana,
                          CalculadorPrazo calculadorPrazo) {
        this.roteirizadorGps   = roteirizadorGps;
        this.rastreador        = rastreador;
        this.seguroCarga       = seguroCarga;
        this.transportadora    = transportadora;
        this.tabelaFrete       = tabelaFrete;
        this.notificadorSms    = notificadorSms;
        this.aduana            = aduana;
        this.calculadorPrazo   = calculadorPrazo;
    }

    /* ----------------------------------------------------------
     *  Orquestração de despacho expresso
     * ---------------------------------------------------------- */
    public void orquestrarDespachoExpresso(EntregaExpressa entrega) {
        // 1. Roteirização GPS
        String rota = roteirizadorGps.gerarRota(entrega.getOrigem(), entrega.getDestino());

        // 2. Cálculo do frete
        double valorFrete = tabelaFrete.calcularFrete(entrega.getPeso(), entrega.getDistancia());

        // 3. Cálculo do prazo
        int prazo = calculadorPrazo.calcularPrazo(entrega.getOrigem(), entrega.getDestino());

        // 4. Seguro de carga
        seguroCarga.assinarSeguro(entrega.getId(), valorFrete);

        // 5. Registro na transportadora
        transportadora.registrarEntrega(entrega.getId(), rota, valorFrete);

        // 6. Notificação SMS
        notificadorSms.enviar(entrega.getClienteTelefone(),
                "Entrega expresso iniciada. R