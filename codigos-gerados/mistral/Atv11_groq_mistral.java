```java
/* ==========================================================
 *  ServicoEntrega (parent class)
 * ==========================================================
 */
public class ServicoEntrega {

    /* Dependencies ----------------------------------------------------- */
    protected RoteirizadorGps roteirizadorGps;
    protected Rastreador rastreador;
    protected SeguroCarga seguroCarga;
    protected Transportadora transportadora;
    protected TabelaFrete tabelaFrete;
    protected NotificadorSms notificadorSms;
    protected Aduana aduana;
    protected CalculadorPrazo calculadorPrazo;

    /* Constructor ----------------------------------------------------- */
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

    /* Orchestration for Express Delivery -------------------------------- */
    public void orquestrarDespachoExpresso(EntregaExpressa entrega) {
        // 1. Roteirização GPS
        String rota = roteirizadorGps.gerarRota(entrega.getOrigem(), entrega.getDestino());

        // 2. Cálculo de frete
        double valorFrete = tabelaFrete.calcularFrete(entrega.getPeso(), entrega.getDistancia());

        // 3. Cálculo de prazo
        int prazo = calculadorPrazo.calcularPrazo(entrega.getOrigem(), entrega.getDestino());

        // 4. Seguro de carga
        seguroCarga.assinarSeguro(entrega.getId(), valorFrete);

        // 5. Registro na transportadora
        transportadora.registrarEntrega(entrega.getId(), rota, valorFrete);

        // 6. Notificação SMS
        notificadorSms.enviar(entrega.getClienteTelefone(),
                "Entrega expressa iniciada. Rota: " + rota + ". Prazo: " + prazo + "