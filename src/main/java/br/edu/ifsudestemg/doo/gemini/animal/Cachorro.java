package br.edu.ifsudestemg.doo.gemini.animal;

public class Cachorro extends Animal {

    public Cachorro(String nome, int idade, Som som, Alimento alimento) {
        super(nome, idade, som, alimento);
    }

    @Override
    public void emitirSom() {
        if (som != null) {
            som.emitir("Latido");
        }
    }

    @Override
    public void movimentar() {
        System.out.println(nome + " está correndo.");
    }

    public void buscar() {
        System.out.println(nome + " está buscando o objeto.");
    }
}