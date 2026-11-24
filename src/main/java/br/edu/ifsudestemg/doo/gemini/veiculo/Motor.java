package br.edu.ifsudestemg.doo.gemini.veiculo;

public class Motor {
    private int potencia; // em cavalos

    public Motor(int potencia) {
        this.potencia = potencia;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    // Método stub para acelerar
    public void acelerar(int rpm) {
        // Implementação vazia – apenas para compilação
    }
}