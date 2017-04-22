package com.hryshchenko.model.weight;

public enum Relevance {
    TITLE(0.1), EXACT_TITLE(0.25), DESCRIPTION(0.1), EXACT_DESCRIPTION(0.15);

    private double value;

    Relevance(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
