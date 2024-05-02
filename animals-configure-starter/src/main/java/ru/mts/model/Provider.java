package ru.mts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "provider")
@Getter
@Setter
@NoArgsConstructor
public class Provider {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @ManyToMany
    @JoinTable(
            name = "animals_providers",
            joinColumns = @JoinColumn(name = "id_animal_type"),
            inverseJoinColumns = @JoinColumn(name = "id_provider")
    )
    private List<AnimalType> animalTypes;

    public Provider(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
