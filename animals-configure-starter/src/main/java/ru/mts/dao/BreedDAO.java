package ru.mts.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.model.Animal;
import ru.mts.model.AnimalType;
import ru.mts.model.Breed;
import ru.mts.util.DBService;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BreedDAO {
    public void saveListBreed(List<Breed> breeds) {
        Session session = DBService.getSessionFactory().getCurrentSession();
        breeds.forEach(session::persist);
    }

    public Animal addAnimal(Breed breed, Animal animal) {
        Session session = DBService.getSessionFactory().getCurrentSession();
        breed.getAnimals().add(animal);

        return animal;
    }
}
