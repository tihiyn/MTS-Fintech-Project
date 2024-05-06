package ru.mts.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.model.Animal;
import ru.mts.util.DBService;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnimalDAO {
    public List<Animal> listAnimals() {
        return DBService.getSessionFactory().getCurrentSession().createQuery("FROM Animal", Animal.class).list();
    }
}
