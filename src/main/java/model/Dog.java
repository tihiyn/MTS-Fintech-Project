package model;

import java.math.BigDecimal;

public class Dog extends Pet {
    // голос собаки
    private static final String voice = "Гав";

    public Dog(String breed, String name, BigDecimal cost, String character) {
        super(breed, name, cost, character);
    }

    // релизация геттера getVoice()
    public String getVoice() {
        return voice;
    }
}
