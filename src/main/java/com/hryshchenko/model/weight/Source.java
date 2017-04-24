package com.hryshchenko.model.weight;

public enum Source {
    PROMETHEUS(0.2), COURSERA(0.2), EDX(0.2), TED(0.1);

    private double value;

    Source(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
