```java
/* ==========================================================
 *  ServicoEntrega – classe base que orquestra o despacho
 *  de entregas (expresso e internacional) usando diversas
 *  dependências (RoteirizadorGps, Rastreador, SeguroCarga,
 *  Transportadora, TabelaFrete, NotificadorSms, Aduana,
 *  CalculadorPrazo).
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

    /* Construtor – injeção de dependências */
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
     *  Orquestra o despacho expresso
     * ---------------------------------------------------------- */
    public void orquestrarDespachoExpresso(Entrega entrega) {
        // 1. Roteirização GPS
        String rota = roteirizadorGps.calcularRota(entrega.getOrigem(), entrega.getDestino());

        // 2. Iniciar rastreamento
        rastreador.iniciarRastreamento(entrega.getId());

        // 3. Seguro de carga
        seguroCarga.assinarSeguro(entrega);

        // 4. Cálculo de custo
        double custo = tabelaFrete.calcularCusto(entrega);

        // 5. Envio via transportadora
        transportadora.enviar(entrega, rota, custo);

        // 6. Notificação ao cliente
        notificadorSms.enviar(entrega.getCliente().getTelefone(),
                              "Entrega expresso iniciada. Rota: " + rota);
    }

    /* ----------------------------------------------------------
     *  Orquestra o despacho internacional
     * ---------------------------------------------------------- */
    public void orquestrarDespachoInternacional(Entrega entrega) {
        // 1. Roteirização GPS
        String rota = roteirizadorGps.calcularRota(entrega.getOrigem(), entrega.getDestino());

        // 2. Iniciar rastreamento
        rastreador.iniciarRastreamento(entrega.getId());

        // 3. Seguro de carga
        seguroCarga.assinarSeguro(entrega);

        // 4. Cálculo de custo (internacional)
        double custo = tabelaFrete.calcularCustoInternacional(entrega);

        // 5. Envio via transportadora
        transportadora.enviar(entrega, rota, custo);

        // 6. Processar despacho na aduana
        aduana.processarDespacho(entrega);

        // 7. Notificação ao cliente
        notificadorSms.enviar(entrega.getCliente().getTelefone(),
                              "Despacho internacional iniciado. Rota: " + rota);
    }

    /* ----------------------------------------------------------
     *  Calcula prazo de entrega (pode ser usado por ambos os tipos)
     * ---------------------------------------------------------- */
    public int calcularPrazoEntrega(Entrega entrega) {
        return calculadorPrazo.calcularPrazo(entrega);
    }
}

/* =================================================