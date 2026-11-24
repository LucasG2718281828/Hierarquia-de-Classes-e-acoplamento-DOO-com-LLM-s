package br.edu.ifsudestemg.doo.gemini.Entrega;

public class CalculadorPrazo {
    public int calcular(double peso, double distancia) {
        // Stub: prazo base 1 dia + 1 dia a cada 500 km
        return 1 + (int) (distancia / 500);
    }
}