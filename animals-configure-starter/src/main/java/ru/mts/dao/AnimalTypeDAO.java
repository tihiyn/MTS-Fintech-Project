package ru.mts.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.mts.model.Animal;
import ru.mts.model.AnimalType;
import ru.mts.util.DBService;

import java.util.List;

@Repository
public class AnimalTypeDAO {
    public List<AnimalType> getListOfAnimalTypes() {
        return DBService.getSessionFactory().getCurrentSession().createQuery("FROM AnimalType", AnimalType.class).list();
    }

    public void saveListAnimalType(List<AnimalType> animalTypes) {
        Session session = DBService.getSessionFactory().getCurrentSession();

        animalTypes.forEach(session::persist);
    }
}
