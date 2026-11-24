package br.edu.ifsudestemg.doo.gemini.Fiscal;

public class Emitente {
    private String cnpj;
    private String nome;

    // Construtor, getters e setters
    public Emitente(String cnpj, String nome) {
        this.cnpj = cnpj;
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}