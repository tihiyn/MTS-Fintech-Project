package ru.mts.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

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
//    @NotEmpty
    private String name;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "character")
    private String character;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "age")
//    @Min(value = 0, message = "Age should be greater than 0")
    private short age;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private AnimalType animalType;

    @ManyToOne
    @JoinColumn(name = "breed_id", referencedColumnName = "id")
    private Breed breed;

    @Column(name = "secret_information")
//    @NotEmpty
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

//    public Animal(String name) {
//        this.name = name;
////        this.age = age;
////        this.secretInformation = secretInformation;
//    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age && Objects.equals(name, animal.name) && Objects.equals(cost, animal.cost) && Objects.equals(character, animal.character) && Objects.equals(birthDate, animal.birthDate) && Objects.equals(animalType, animal.animalType) && Objects.equals(breed, animal.breed) && Objects.equals(secretInformation, animal.secretInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost, character, birthDate, age, animalType, breed, secretInformation);
    }
}
