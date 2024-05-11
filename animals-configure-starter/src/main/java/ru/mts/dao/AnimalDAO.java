package ru.mts.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.mts.model.Animal;
import ru.mts.util.DBService;

import java.util.List;

@Repository
public class AnimalDAO {
    public List<Animal> getListOfAnimals() {
        return DBService.getSessionFactory().getCurrentSession().createQuery("FROM Animal", Animal.class).list();
    }

    public void saveAnimal(Animal animal) {
        DBService.getSessionFactory().getCurrentSession().persist(animal);
    }

    public void saveListOfAnimals(List<Animal> animals) {
        Session session = DBService.getSessionFactory().getCurrentSession();
        animals.forEach(session::persist);
    }

    public void deleteAll() {
        Session session = DBService.getSessionFactory().getCurrentSession();

        List<Animal> animals = getListOfAnimals();
        for (Animal animal: animals) {
            session.remove(animal);
        }
    }
}
