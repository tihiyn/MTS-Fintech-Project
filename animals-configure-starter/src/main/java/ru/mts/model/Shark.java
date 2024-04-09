package ru.mts.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Shark extends Predator {
    private static final String voice = "Буль";

    public Shark(String breed, String name, BigDecimal cost, String character, LocalDate birthDate, String secretInformation) {
        super(breed, name, cost, character, birthDate, secretInformation);
    }

    public String getVoice() {
        return voice;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
//    @JsonValue
    public String toString() {
        return "Shark{" +
                "'breed':'" + breed  +
                "', 'name':'" + name +
                "', 'cost':" + cost +
                ", 'character':'" + character +
                "', 'birthDate':'" + birthDate +
                "', 'secretInformation':'" + secretInformation +
                "'}";
    }
}