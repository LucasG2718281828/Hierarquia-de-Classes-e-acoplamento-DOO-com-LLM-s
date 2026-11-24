package br.edu.ifsudestemg.doo.mistral.animal;

public class Gato extends Animal {
    private boolean castrado;

    public Gato(String nome, int idade, Som som, Alimento alimento, boolean castrado) {
        super(nome, idade, som, alimento);
        this.castrado = castrado;
    }

    @Override
    public void emitirSom() {
        System.out.println(this.nome + " diz: Miau!");
    }

    @Override
    public void movimentar() {
        System.out.println(this.nome + " está saltando.");
    }

    public void lamberPata() {
        System.out.println(this.nome + " está lambendo a pata.");
    }

    public boolean isCastrado() {
        return castrado;
    }

    public void setCastrado(boolean castrado) {
        this.castrado = castrado;
    }
}