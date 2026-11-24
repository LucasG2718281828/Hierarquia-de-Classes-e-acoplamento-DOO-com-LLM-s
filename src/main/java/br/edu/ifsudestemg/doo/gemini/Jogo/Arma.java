package br.edu.ifsudestemg.doo.gemini.Jogo;

public class Arma {
    private String nome;
    private double dano;

    public Arma(String nome, double dano) {
        this.nome = nome;
        this.dano = dano;
    }

    public String getNome() { return nome; }
    public double getDano() { return dano; }

    // Métodos adicionais podem ser adicionados aqui
}