package br.edu.ifsudestemg.doo.gemini.contabancaria;

import java.text.NumberFormat;
import java.util.Locale;

public class Moeda {
    private String simbolo;
    private Locale locale;

    public Moeda(String simbolo, Locale locale) {
        this.simbolo = simbolo;
        this.locale = locale;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public Locale getLocale() {
        return locale;
    }

    public String formatar(double valor) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        return nf.format(valor);
    }

    // Stubs para possíveis conversões ou outras operações.
}