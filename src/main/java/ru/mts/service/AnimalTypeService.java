package ru.mts.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.model.AnimalType;
import ru.mts.repository.AnimalTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalTypeService {
    private final AnimalTypeRepository animalTypeRepository;
    private final EntityManager entityManager;

    public List<AnimalType> getAnimalTypes() {
        return animalTypeRepository.findAll();
    }

    public AnimalType getAnimalTypeByType(String type) {
        return animalTypeRepository.getAnimalTypeByType(type);
    }
}
