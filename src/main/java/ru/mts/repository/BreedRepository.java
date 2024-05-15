package ru.mts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.model.Breed;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Integer> {
}
