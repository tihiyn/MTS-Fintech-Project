package model;

import dto.Predator;

import java.math.BigDecimal;

public class Shark extends Predator {
    // голос акулы
    private static final String voice = "Буль";

    public Shark(String breed, String name, BigDecimal cost, String character) {
        super(breed, name, cost, character);
    }

    // релизация геттера getVoice()
    public String getVoice() {
        return voice;
    }
}