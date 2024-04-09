package ru.mts.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Pet extends AbstractAnimal {
//    public Pet(String json) {
//        super(json);
//    }

    public Pet(String breed, String name, BigDecimal cost, String character, LocalDate birthDate,  String secretInformation) {
        super(breed, name, cost, character, birthDate, secretInformation);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
