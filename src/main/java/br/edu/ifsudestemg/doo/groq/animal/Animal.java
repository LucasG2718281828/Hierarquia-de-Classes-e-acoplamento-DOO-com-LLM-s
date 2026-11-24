package br.edu.ifsudestemg.doo.groq.animal;

public abstract class Animal {
    protected String nome;
    protected int idade;
    protected Som som;
    protected Alimento alimento;

    public Animal(String nome, int idade, Som som, Alimento alimento) {
        this.nome = nome;
        this.idade = idade;
        this.som = som;
        this.alimento = alimento;
    }

    public abstract void emitirSom();

    public abstract void movimentar();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Som getSom() {
        return som;
    }

    public void setSom(Som som) {
        this.som = som;
    }

    public Alimento getAlimento() {
        return alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }
}