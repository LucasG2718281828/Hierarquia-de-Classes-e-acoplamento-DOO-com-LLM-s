package br.edu.ifsudestemg.doo.gemini.Fiscal;

public class Destinatario {
    private String cpfCnpj;
    private String nome;

    // Construtor, getters e setters
    public Destinatario(String cpfCnpj, String nome) {
        this.cpfCnpj = cpfCnpj;
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}