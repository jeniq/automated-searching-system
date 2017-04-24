package com.hryshchenko.model.weight;

public enum View {
    COEFFICIENT(0.1);

    private double value;

    View(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
