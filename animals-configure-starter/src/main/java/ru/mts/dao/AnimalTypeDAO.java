package ru.mts.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.model.Animal;
import ru.mts.model.AnimalType;
import ru.mts.util.DBService;

import java.util.List;

@Repository
//@RequiredArgsConstructor
public class AnimalTypeDAO {
    public List<AnimalType> listAnimalTypes() {
        return DBService.getSessionFactory().getCurrentSession().createQuery("FROM AnimalType", AnimalType.class).list();
    }

    public void saveListAnimalType(List<AnimalType> animalTypes) {
        Session session = DBService.getSessionFactory().getCurrentSession();

        animalTypes.forEach(session::persist);
    }

    public Animal addAnimal(AnimalType animalType, Animal animal) {
        Session session = DBService.getSessionFactory().getCurrentSession();

        animalType.getAnimals().add(animal);
        return animal;
    }
}
