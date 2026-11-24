package br.edu.ifsudestemg.doo.groq.Jogo;

public abstract class PersonagemJogo {
    protected String nome;
    protected int vida;
    protected int ataqueBase;
    protected int defesaBase;
    protected Arma arma;
    protected Armadura armadura;
    protected Inventario inventario;
    protected EfeitoStatus efeitoStatus;

    public PersonagemJogo(String nome, int vida, int ataqueBase, int defesaBase) {
        this.nome = nome;
        this.vida = vida;
        this.ataqueBase = ataqueBase;
        this.defesaBase = defesaBase;
        this.inventario = new Inventario();
    }

    public void equiparArma(Arma arma) {
        this.arma = arma;
    }

    public void equiparArmadura(Armadura armadura) {
        this.armadura = armadura;
    }

    public void aplicarEfeito(EfeitoStatus efeito) {
        this.efeitoStatus = efeito;
    }

    public int calcularDano() {
        int dano = ataqueBase;
        if (arma != null) {
            dano += arma.getDano();
        }
        if (efeitoStatus != null) {
            dano = efeitoStatus.modificarDano(dano);
        }
        return dano;
    }

    public void receberDano(int dano) {
        int defesa = defesaBase;
        if (armadura != null) {
            defesa += armadura.getDefesa();
        }
        int danoFinal = dano - defesa;
        if (danoFinal < 0) danoFinal = 0;
        vida -= danoFinal;
        if (vida < 0) vida = 0;
    }

    public void processarCombate(PersonagemJogo adversario) {
        int danoDoAtacante = this.calcularDano();
        adversario.receberDano(danoDoAtacante);
    }

    // Getters and setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    public void setAtaqueBase(int ataqueBase) {
        this.ataqueBase = ataqueBase;
    }

    public int getDefesaBase() {
        return defesaBase;
    }

    public void setDefesaBase(int defesaBase) {
        this.defesaBase = defesa