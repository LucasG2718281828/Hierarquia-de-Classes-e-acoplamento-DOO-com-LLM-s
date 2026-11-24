package br.edu.ifsudestemg.doo.groq.animal;

public class Gato extends Animal {

    public Gato(String nome, int idade, Som som, Alimento alimento) {
        super(nome, idade, som, alimento);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " diz: " + som.getSom());
    }

    @Override
    public void movimentar() {
        System.out.println(nome + " anda silenciosamente.");
    }
}