package br.edu.ifsudestemg.doo.gemini.Pagamento;

public class ReciboFactory {
    public Recibo gerarRecibo(PagamentoInfo info) {
        // Stub: pode conter lógica de template ou formatação
        return new Recibo(info);
    }
}