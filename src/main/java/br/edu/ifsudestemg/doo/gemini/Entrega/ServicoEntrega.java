package br.edu.ifsudestemg.doo.gemini.Entrega;

public class ServicoEntrega {

    protected RoteirizadorGps roteirizador;
    protected Rastreador rastreador;
    protected SeguroCarga seguroCarga;
    protected Transportadora transportadora;
    protected TabelaFrete tabelaFrete;
    protected NotificadorSms notificadorSms;
    protected Aduana aduana;
    protected CalculadorPrazo calculadorPrazo;

    public ServicoEntrega(RoteirizadorGps roteirizador,
                          Rastreador rastreador,
                          SeguroCarga seguroCarga,
                          Transportadora transportadora,
                          TabelaFrete tabelaFrete,
                          NotificadorSms notificadorSms,
                          Aduana aduana,
                          CalculadorPrazo calculadorPrazo) {
        this.roteirizador = roteirizador;
        this.rastreador = rastreador;
        this.seguroCarga = seguroCarga;
        this.transportadora = transportadora;
        this.tabelaFrete = tabelaFrete;
        this.notificadorSms = notificadorSms;
        this.aduana = aduana;
        this.calculadorPrazo = calculadorPrazo;
    }

    /**
     * Orquestra o despacho de uma entrega expressa.
     */
    public void orquestrarDespachoExpressa(Pedido pedido) {
        // 1. Roteirização GPS
        Rota rota = roteirizador.roteirizar(pedido.getEnderecoOrigem(), pedido.getEnderecoDestino());

        // 2. Cálculo de frete
        double valorFrete = tabelaFrete.calcular(pedido.getPeso(), pedido.getVolume(), rota.getDistancia());

        // 3. Cálculo de prazo
        int prazoDias = calculadorPrazo.calcular(pedido.getPeso(), rota.getDistancia());

        // 4. Contratação de seguro de carga
        seguroCarga.contratar(pedido.getValorMercadoria());

        // 5. Agendamento de transporte
        transportadora.agendar(pedido, rota);

        // 6. Início do rastreamento
        rastreador.iniciar(pedido.getId(), rota);

        // 7. Notificação ao cliente
        String mensagem = String.format(
                "Seu pedido %s foi despachado. Prazo estimado: %d dias. Valor do frete: R$ %.2f.",
                pedido.getId(), prazoDias, valorFrete);
        notificadorSms.enviar(pedido.getClienteTelefone(), mensagem);
    }

    /**
     * Calcula o valor do frete para um pedido.
     */
    public double calcularFrete(Pedido pedido) {
        Rota rota = roteirizador.roteirizar(pedido.getEnderecoOrigem(), pedido.getEnderecoDestino());
        return tabelaFrete.calcular(pedido.getPeso(), pedido.getVolume(), rota.getDistancia());
    }

    /**
     * Calcula o prazo em dias para um pedido.
     */
    public int calcularPrazo(Pedido pedido) {
        Rota rota = roteirizador.roteirizar(pedido.getEnderecoOrigem(), pedido.getEnderecoDestino());
        return calculadorPrazo.calcular(pedido.getPeso(), rota.getDistancia());
    }

    // Getters e Setters (opcional)
}