package ru.mts.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.mts.model.Animal;
import ru.mts.model.Breed;
import ru.mts.util.DBService;

import java.util.List;

@Repository
public class BreedDAO {
    public void saveListOfBreeds(List<Breed> breeds) {
        Session session = DBService.getSessionFactory().getCurrentSession();
        breeds.forEach(session::persist);
    }
}
