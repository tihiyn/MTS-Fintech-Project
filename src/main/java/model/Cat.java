package model;

import java.math.BigDecimal;

public class Cat extends Pet {
    // голос кошки
    private static final String voice = "Мяу";

    public Cat(String breed, String name, BigDecimal cost, String character) {
        super(breed, name, cost, character);
    }

    // релизация геттера getVoice()
    public String getVoice() {
        return voice;
    }
}
