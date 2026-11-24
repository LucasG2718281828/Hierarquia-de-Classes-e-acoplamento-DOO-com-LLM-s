package br.edu.ifsudestemg.doo.gemini.animal;

public class Gato extends Animal {

    public Gato(String nome, int idade, Som som, Alimento alimento) {
        super(nome, idade, som, alimento);
    }

    @Override
    public void emitirSom() {
        if (som != null) {
            som.emitir("Miado");
        }
    }

    @Override
    public void movimentar() {
        System.out.println(nome + " está andando silenciosamente.");
    }

    public void arranhar() {
        System.out.println(nome + " está arranhando o móvel.");
    }
}