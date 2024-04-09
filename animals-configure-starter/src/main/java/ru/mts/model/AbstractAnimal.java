package ru.mts.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;

public abstract class AbstractAnimal implements Animal {
    protected String breed;
    protected String name;
    protected BigDecimal cost;
    protected String character;
    protected LocalDate birthDate;
    protected String secretInformation;

//    public AbstractAnimal(String json) {
//        this.breed = json.substring(json.indexOf("'breed':") + 9, json.indexOf(",", json.indexOf("'breed':")) - 1);
//        this.name = json.substring(json.indexOf("'name':") + 8, json.indexOf(",", json.indexOf("'name':")) - 1);
//        this.cost = new BigDecimal(json.substring(json.indexOf("'cost':") + 7, json.indexOf(",", json.indexOf("'cost'"))));
//        this.character = json.substring(json.indexOf("'character':") + 13, json.indexOf(",", json.indexOf("'character':")) - 1);
//        this.birthDate = LocalDate.parse(json.substring(json.indexOf("'birthDate':") + 13, json.indexOf(",", json.indexOf("'birthDate':")) - 1));
//        this.secretInformation = new String(Base64.getDecoder().decode(json.substring(json.indexOf("'secretInformation':") + 21, json.indexOf("}", json.indexOf("'secretInformation':")) - 1)));
//    }

    public AbstractAnimal(String breed, String name, BigDecimal cost, String character, LocalDate birthDate, String secretInformation) {
        this.breed = breed;
        this.name = name;
        this.cost = cost;
        this.character = character;
        this.birthDate = birthDate;
        this.secretInformation = secretInformation;
    }

    @Override
    public String getBreed() {
        return breed;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public String getCharacter() {
        return character;
    }

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public String getSecretInformation() {
        return secretInformation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractAnimal that = (AbstractAnimal) o;
        return Objects.equals(breed, that.breed) && Objects.equals(name, that.name) && Objects.equals(cost, that.cost) && Objects.equals(character, that.character) && Objects.equals(birthDate, that.birthDate) && Objects.equals(secretInformation, that.secretInformation);
    }


    @Override
    public int hashCode() {
        return Objects.hash(breed, name, cost, character, birthDate, secretInformation);
    }
}
