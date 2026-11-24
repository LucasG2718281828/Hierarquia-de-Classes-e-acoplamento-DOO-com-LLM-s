package br.edu.ifsudestemg.doo.groq.dispostitivosEletronicos;

public class Bateria {
    private int capacity; // em mAh
    private double voltage; // em volts

    public Bateria(int capacity, double voltage) {
        this.capacity = capacity;
        this.voltage = voltage;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public void recharge() {
        // Stub: lógica de recarga
        System.out.println("Recargando bateria de " + capacity + "mAh");
    }

    @Override
    public String toString() {
        return "Bateria{" +
                "capacity=" + capacity +
                ", voltage=" + voltage +
                '}';
    }
}