package ru.mts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.model.Breed;
import ru.mts.repository.BreedRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BreedService {
    private final BreedRepository breedRepository;

    public List<Breed> getBreeds() {
        return breedRepository.findAll();
    }
}
