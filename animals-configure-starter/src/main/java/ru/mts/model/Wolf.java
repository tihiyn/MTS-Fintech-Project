package ru.mts.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Wolf extends Predator {
    private static final String voice = "Аууууу";

    public Wolf(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
        super(breed, name, cost, character, birthDate);
    }

    public String getVoice() {
        return voice;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}