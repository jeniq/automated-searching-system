package com.hryshchenko.model.weight;

public enum Language {
    UKRAINIAN(0.4), RUSSIAN(0.3), ENGLISH(0.1), OTHER(0);

    private double value;

    Language(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
