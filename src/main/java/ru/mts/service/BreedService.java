package ru.mts.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.model.AnimalType;
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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Breed> criteriaQuery = criteriaBuilder.createQuery(Breed.class);
        Root<Breed> root = criteriaQuery.from(Breed.class);

        Predicate breedPredicate = criteriaBuilder.equal(root.get("breed"), breed);
        criteriaQuery.where(breedPredicate);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
