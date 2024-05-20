package ru.mts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "breed")
@Getter
@Setter
@NoArgsConstructor
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
                "id=" + id +
                ", breed='" + breed + '\'' +
                ", animalType=" + animalType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Breed breed1 = (Breed) o;
        return Objects.equals(breed, breed1.breed) && Objects.equals(animalType, breed1.animalType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(breed, animalType);
    }
}
