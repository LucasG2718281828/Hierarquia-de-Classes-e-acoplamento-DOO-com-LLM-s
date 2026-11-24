package br.edu.ifsudestemg.doo.mistral.veiculo;

public class Placa {
    private String numero;
    private String estado;

    public Placa(String numero, String estado) {
        this.numero = numero;
        this.estado = estado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Placa{" +
                "numero='" + numero + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}