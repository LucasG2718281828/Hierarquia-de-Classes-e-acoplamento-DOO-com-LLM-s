package br.edu.ifsudestemg.doo.mistral.Fiscal;

public class Emitente {
    private String nome;
    private String cnpj;

    public Emitente() {
    }

    public Emitente(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
    }

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}