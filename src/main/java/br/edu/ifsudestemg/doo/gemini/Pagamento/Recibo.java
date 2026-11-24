package br.edu.ifsudestemg.doo.gemini.Pagamento;

public class Recibo {
    private PagamentoInfo info;

    public Recibo(PagamentoInfo info) {
        this.info = info;
    }

    public void imprimir() {
        // Stub: lógica de impressão ou geração de PDF
        System.out.println("Recibo - Tipo: " + info.getTipoMeioPagamento()
                + ", Valor: " + info.getValor() + " " + info.getMoeda()
                + ", Descrição: " + info.getDescricao());
    }
}