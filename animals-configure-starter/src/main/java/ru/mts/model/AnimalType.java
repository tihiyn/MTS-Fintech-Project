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
@Table(name = "animal_type")
@NoArgsConstructor
@Getter
@Setter
//@ToString
public class AnimalType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "is_wild")
    private boolean isWild;

    @ManyToMany
    @JsonIgnore
    private List<Habitat> habitats;

    @ManyToMany
    @JsonIgnore
    private List<Provider> providers;

    @OneToMany(mappedBy = "animalType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Animal> animals;

    @OneToMany(mappedBy = "animalType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Breed> breeds;

    public AnimalType(String type, boolean isWild) {
        this.type = type;
        this.isWild = isWild;
    }

    @Override
    public String toString() {
        return "AnimalType{" +
                "id:" + id +
                ", type:'" + type + '\'' +
                ", isWild:" + isWild +
                '}';
    }
}
