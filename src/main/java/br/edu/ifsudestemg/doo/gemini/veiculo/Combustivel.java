package br.edu.ifsudestemg.doo.gemini.veiculo;

public class Combustivel {
    private String tipo; // ex.: "Gasolina", "Álcool", "Diesel"

    public Combustivel(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}