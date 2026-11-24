package br.edu.ifsudestemg.doo.mistral.animal;

public class Cachorro extends Animal {
    private String raca;

    public Cachorro(String nome, int idade, Som som, Alimento alimento, String raca) {
        super(nome, idade, som, alimento);
        this.raca = raca;
    }

    @Override
    public void emitirSom() {
        System.out.println(this.nome + " diz: Au au!");
    }

    @Override
    public void movimentar() {
        System.out.println(this.nome + " está correndo.");
    }

    public void buscarObjeto() {
        System.out.println(this.nome + " está buscando o objeto.");
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}