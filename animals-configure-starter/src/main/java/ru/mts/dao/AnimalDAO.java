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
public class AnimalDAO {
    public List<Animal> listAnimals() {
        return DBService.getSessionFactory().getCurrentSession().createQuery("FROM Animal", Animal.class).list();
    }

    public void saveListAnimalType(List<Animal> animals) {
        Session session = DBService.getSessionFactory().getCurrentSession();
        animals.forEach(session::persist);
    }

    public void deleteAll() {
        Session session = DBService.getSessionFactory().getCurrentSession();

        List<Animal> animals = listAnimals();
        for (Animal animal: animals) {
            session.remove(animal);
        }
    }
}
