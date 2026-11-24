package br.edu.ifsudestemg.doo.mistral.contabancaria;

public class Moeda {
    private String codigo;
    private String simbolo;

    public Moeda(String codigo, String simbolo) {
        this.codigo = codigo;
        this.simbolo = simbolo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
}