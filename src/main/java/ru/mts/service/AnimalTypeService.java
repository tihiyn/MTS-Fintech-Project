package ru.mts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.model.AnimalType;
import ru.mts.repository.AnimalTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalTypeService {
    private final AnimalTypeRepository animalTypeRepository;

    public List<AnimalType> getAnimalTypes() {
        return animalTypeRepository.findAll();
    }
}
