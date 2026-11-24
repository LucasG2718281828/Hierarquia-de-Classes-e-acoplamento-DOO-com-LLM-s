package br.edu.ifsudestemg.doo.gemini.Jogo;

import java.util.*;

public abstract class PersonagemJogo {
    protected String nome;
    protected int nivel;
    protected double vida;
    protected double ataqueBase;
    protected double defesaBase;
    protected Arma arma;
    protected Armadura armadura;
    protected Inventario inventario;
    protected List<EfeitoStatus> efeitosAtivos;

    public PersonagemJogo(String nome, int nivel, double vida, double ataqueBase, double defesaBase) {
        this.nome = nome;
        this.nivel = nivel;
        this.vida = vida;
        this.ataqueBase = ataqueBase;
        this.defesaBase = defesaBase;
        this.efeitosAtivos = new ArrayList<>();
        this.inventario = new Inventario();
    }

    // Processa o combate contra outro personagem
    public void processarCombate(PersonagemJogo oponente) {
        double dano = this.calcularDano();
        oponente.receberDano(dano);
    }

    // Calcula o dano considerando arma, efeitos e atributos
    public double calcularDano() {
        double dano = ataqueBase;
        if (arma != null) {
            dano += arma.getDano();
        }
        for (EfeitoStatus e : efeitosAtivos) {
            dano = e.modificarDano(dano);
        }
        return dano;
    }

    // Recebe dano, considerando armadura e efeitos
    public void receberDano(double dano) {
        double mitigacao = defesaBase;
        if (armadura != null) {
            mitigacao += armadura.getDefesa();
        }
        for (EfeitoStatus e : efeitosAtivos) {
            mitigacao = e.modificarDefesa(mitigacao);
        }
        double danoEfetivo = Math.max(0, dano - mitigacao);
        vida -= danoEfetivo;
        if (vida < 0) vida = 0;
    }

    public void aplicarEfeito(EfeitoStatus efeito) {
        efeitosAtivos.add(efeito);
    }

    public void removerEfeito(EfeitoStatus efeito) {
        efeitosAtivos.remove(efeito);
    }

    // Getters e Setters básicos
    public String getNome() { return nome; }
    public double getVida() { return vida; }
    public void setArma(Arma arma) { this.arma = arma; }
    public void setArmadura(Armadura armadura) { this.armadura = armadura; }
    public Inventario getInventario() { return inventario; }
}