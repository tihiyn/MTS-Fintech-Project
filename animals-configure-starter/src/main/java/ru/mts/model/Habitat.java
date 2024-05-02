package ru.mts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "habitat")
public class Habitat {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "area")
    private String area;

    @ManyToMany
    @JoinTable(
            name = "animals_habitats",
            joinColumns = @JoinColumn(name = "id_animal_type"),
            inverseJoinColumns = @JoinColumn(name = "id_area")
    )
    private List<AnimalType> animalTypes;

    public Habitat(String area) {
        this.area = area;
    }
}
