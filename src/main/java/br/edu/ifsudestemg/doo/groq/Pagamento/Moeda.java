package br.edu.ifsudestemg.doo.groq.Pagamento;

import java.text.NumberFormat;
import java.util.Locale;

public class Moeda {
    private Locale locale;

    public Moeda(Locale locale) {
        this.locale = locale;
    }

    public String formatar(double valor) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        return nf.format(valor);
    }
}