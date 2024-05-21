package ru.mts.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AnimalType> criteriaQuery = criteriaBuilder.createQuery(AnimalType.class);
        Root<AnimalType> root = criteriaQuery.from(AnimalType.class);

        Predicate typePredicate = criteriaBuilder.equal(root.get("type"), type);
        criteriaQuery.where(typePredicate);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
