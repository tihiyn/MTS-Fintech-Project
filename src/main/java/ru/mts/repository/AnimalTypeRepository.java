package ru.mts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.model.AnimalType;

@Repository
public interface AnimalTypeRepository extends JpaRepository<AnimalType, Integer> {
    AnimalType getAnimalTypeByType(String type);
}
