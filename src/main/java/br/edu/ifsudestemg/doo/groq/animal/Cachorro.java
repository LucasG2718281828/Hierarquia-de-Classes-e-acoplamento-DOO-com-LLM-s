package br.edu.ifsudestemg.doo.groq.animal;

public class Cachorro extends Animal {

    public Cachorro(String nome, int idade, Som som, Alimento alimento) {
        super(nome, idade, som, alimento);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " diz: " + som.getSom());
    }

    @Override
    public void movimentar() {
        System.out.println(nome + " corre.");
    }
}