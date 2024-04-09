package ru.mts.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;

public class Cat extends Pet {
    private static final String voice = "Мяу";

//    public Cat(String json) {
//        super(json);
//    }

    public Cat(String breed, String name, BigDecimal cost, String character, LocalDate birthDate, String secretInformation) {
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
        return "Cat{" +
                "'breed':'" + breed  +
                "', 'name':'" + name +
                "', 'cost':" + cost +
                ", 'character':'" + character +
                "', 'birthDate':'" + birthDate +
                "', 'secretInformation':'" + secretInformation +
                "'}";
    }
}
