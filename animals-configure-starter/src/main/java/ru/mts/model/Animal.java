package ru.mts.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "creature")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Animal {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "character")
    private String character;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "age")
    private short age;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private AnimalType animalType;

    @ManyToOne
    @JoinColumn(name = "breed_id", referencedColumnName = "id")
    private Breed breed;

    @Column(name = "secret_information")
    private String secretInformation;


    public Animal(String name, BigDecimal cost, String character, LocalDate birthDate, AnimalType animalType, short age, Breed breed, String secretInformation) {
        this.name = name;
        this.cost = cost;
        this.character = character;
        this.birthDate = birthDate;
        this.animalType = animalType;
        this.age = age;
        this.breed = breed;
        this.secretInformation = secretInformation;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name:'" + name + '\'' +
                ", cost:" + cost +
                ", character:'" + character + '\'' +
                ", birthDate:" + birthDate +
                ", age:" + age +
                ", animalType:" + animalType +
                ", breed:" + breed +
                ", secretInformation:'" + secretInformation + '\'' +
                '}';
    }
}
