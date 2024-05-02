package ru.mts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "breed")
@Getter
@Setter
@NoArgsConstructor
//@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Breed {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "breed")
    private String breed;

    @OneToMany(mappedBy = "breed", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Animal> animals;

    @ManyToOne
    @JoinColumn(name = "animal_type_id", referencedColumnName = "id")
    @JsonIgnore
    private AnimalType animalType;

    public Breed(String breed, AnimalType animalType) {
        this.breed = breed;
        this.animalType = animalType;
    }

    @Override
    public String toString() {
        return "Breed{" +
                "id:" + id +
                ", breed:'" + breed + '\'' +
                '}';
    }
}
