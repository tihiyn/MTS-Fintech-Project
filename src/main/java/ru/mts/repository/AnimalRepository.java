package ru.mts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.exceptions.IllegalCollectionSizeException;
import ru.mts.exceptions.NegativeArgumentException;
import ru.mts.model.Animal;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {

}
