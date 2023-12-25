package model;

import java.math.BigDecimal;

public class Wolf extends Predator {
    // голос волка
    private static final String voice = "Аууууу";

    public Wolf(String breed, String name, BigDecimal cost, String character) {
        super(breed, name, cost, character);
    }

    // релизация геттера getVoice()
    public String getVoice() {
        return voice;
    }
}