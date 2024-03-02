package ru.mts.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cat extends Pet {
    private static final String voice = "Мяу";

    public Cat(String breed, String name, BigDecimal cost, String character, LocalDate birthDate) {
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
