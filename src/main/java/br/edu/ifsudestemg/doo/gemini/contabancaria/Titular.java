package br.edu.ifsudestemg.doo.gemini.contabancaria;

public class Titular {
    private String nome;
    private String cpf;

    public Titular(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    // Setters, equals, hashCode e toString podem ser implementados conforme necessidade.
}