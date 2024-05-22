package ru.mts.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.model.Breed;
import ru.mts.repository.BreedRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BreedService {
    private final BreedRepository breedRepository;
    private final EntityManager entityManager;

    public List<Breed> getBreeds() {
        return breedRepository.findAll();
    }

    public Breed getBreedByBreed(String breed) {
        return breedRepository.getBreedByBreed(breed);
    }
}
