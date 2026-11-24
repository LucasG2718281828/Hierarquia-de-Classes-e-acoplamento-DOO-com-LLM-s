package br.edu.ifsudestemg.doo.mistral.Pagamento;

public class Pix extends MeioDePagamento {
    private String chavePix;
    private String bancoDestino;

    public Pix(String chavePix, String bancoDestino, GatewayPagamento gateway, ValidadorFraude validador, Notificacao notificacao) {
        super(gateway, validador, notificacao);
        this.chavePix = chavePix;
        this.bancoDestino = bancoDestino;
    }

    @Override
    public boolean efetuarPagamento(double valor, Moeda moeda) {
        boolean fraudeOk = validador.validar(chavePix, valor);
        if (!fraudeOk) {
            notificacao.enviar("Pagamento Pix recusado por suspeita de fraude.");
            return false;
        }
        boolean processado = gateway.processarTransacao(valor, moeda);
        if (processado) {
            emitirRecibo(valor, moeda);
            notificacao.enviar("Pagamento Pix aprovado.");
        } else {
            notificacao.enviar("Falha no processamento do Pix.");
        }
        return processado;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }

    public String getBancoDestino() {
        return bancoDestino;
    }

    public void setBancoDestino(String bancoDestino) {
        this.bancoDestino = bancoDestino;
    }
}