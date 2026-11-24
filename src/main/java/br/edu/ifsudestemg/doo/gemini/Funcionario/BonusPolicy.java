package br.edu.ifsudestemg.doo.gemini.Funcionario;

public interface BonusPolicy {
    /**
     * Calcula o bônus padrão para o funcionário.
     */
    double aplicarBonus(Funcionario funcionario);

    /**
     * Calcula o bônus a partir de um valor base já calculado.
     */
    default double aplicarBonus(Funcionario funcionario, double valorBase) {
        // Implementação padrão: não altera o valor base.
        return valorBase;
    }
}