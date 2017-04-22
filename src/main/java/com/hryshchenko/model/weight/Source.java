package com.hryshchenko.model.weight;

public enum Source {
    PROMETHEUS(0.35), COURSERA(0.3), EDX(0.3), TED(0.2);

    private double value;

    Source(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
