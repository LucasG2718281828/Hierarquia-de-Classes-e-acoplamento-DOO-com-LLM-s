package br.edu.ifsudestemg.doo.gemini.Jogo;

public class Guerreiro extends PersonagemJogo {

    private double bonusAtaqueForte = 10.0;

    public Guerreiro(String nome, int nivel) {
        super(nome, nivel, 150 + nivel * 10, 20 + nivel * 2, 15 + nivel);
    }

    // Ataque especial que aumenta o dano temporariamente
    public void ataqueForte(PersonagemJogo oponente) {
        double danoOriginal = this.calcularDano();
        double danoEspecial = danoOriginal + bonusAtaqueForte;
        oponente.receberDano(danoEspecial);
    }

    @Override
    public double calcularDano() {
        double dano = super.calcularDano();
        // Guerreiro tem um pequeno bônus de ataque por nível
        dano += nivel * 0.5;
        return dano;
    }
}