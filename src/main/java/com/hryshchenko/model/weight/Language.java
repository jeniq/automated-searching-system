package com.hryshchenko.model.weight;

public enum Language {
    UKRAINIAN(0.1), RUSSIAN(0.1), ENGLISH(0.1), OTHER(0), NOT_MATCH(0), MATCH(0.2);

    private double value;

    Language(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
