package br.edu.ifsudestemg.doo.gemini.Jogo;

public class Mago extends PersonagemJogo {

    private double bonusMagia = 12.0;

    public Mago(String nome, int nivel) {
        super(nome, nivel, 100 + nivel * 8, 25 + nivel * 3, 8 + nivel);
    }

    // Ataque mágico que ignora parte da defesa do oponente
    public void ataqueMagico(PersonagemJogo oponente) {
        double dano = this.calcularDano() + bonusMagia;
        // Reduz a defesa do oponente em 20% para este ataque
        double defesaOriginal = oponente.defesaBase;
        oponente.defesaBase *= 0.8;
        oponente.receberDano(dano);
        oponente.defesaBase = defesaOriginal; // restaura
    }

    @Override
    public double calcularDano() {
        double dano = super.calcularDano();
        // Mago tem bônus de dano mágico por nível
        dano += nivel * 0.7;
        return dano;
    }
}