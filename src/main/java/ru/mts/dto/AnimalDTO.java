package ru.mts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

//@AllArgsConstructor
@Getter
@Setter
public class AnimalDTO {
    private long id;
    private String name;
    private BigDecimal cost;
    private String character;
    private LocalDate birthDate;
    private String animalType;
    private short age;
    private String breed;
    private String secretInformation;

    public AnimalDTO(String name, BigDecimal cost, String character, LocalDate birthDate, String animalType, short age, String breed, String secretInformation) {
        this.name = name;
        this.cost = cost;
        this.character = character;
        this.birthDate = birthDate;
        this.animalType = animalType;
        this.age = age;
        this.breed = breed;
        this.secretInformation = secretInformation;
    }
}
