package ru.mts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import ru.mts.exceptions.IllegalCollectionSizeException;
import ru.mts.exceptions.NegativeArgumentException;
import ru.mts.model.Animal;
import ru.mts.service.CreateAnimalService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Repository
@Scope("prototype")
public class AnimalsRepositoryImpl implements AnimalsRepository {

    private static Logger logger = LoggerFactory.getLogger(AnimalsRepositoryImpl.class);
    private final SessionFactory sessionFactory;
    @Autowired
    private ObjectMapper objectMapper;
    private ConcurrentMap<String, List<Animal>> animalStorage;

    private CreateAnimalService createAnimalService;

    public AnimalsRepositoryImpl(SessionFactory sessionFactory, CreateAnimalService createAnimalService) {
        this.sessionFactory = sessionFactory;
        this.createAnimalService = createAnimalService;
    }

    @Override
    public Map<String, List<Animal>> getAnimalStorage() {
        return animalStorage;
    }

    /**
     * PostConstruct-метод для наполнения "хранилища"
     *
     * @author Nikita
     * @since 1.4
     */
    @PostConstruct
    @Override
    public void fillStorage() {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            animalStorage = session.createQuery("FROM Animal", Animal.class).list().stream()
                    .collect(Collectors.groupingByConcurrent(animal -> animal.getAnimalType().getType()));

            session.getTransaction().commit();
        } finally {
            session.close();
        }


//        logger.info("Типы созданных животных");
//        for (AnimalEnum type : createAnimalService.receiveAnimalTypes()) {
//            logger.info(String.valueOf(type));
//        }
    }

    @Override
    public Map<String, LocalDate> findLeapYearNames() {
        Path path = Paths.get("src/main/resources/results/findLeapYearNames.json");
        File file = new File(path.toString());

        Map<String, LocalDate> outputMap = animalStorage.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .flatMap(entry -> entry.getValue().stream()
                        .filter(animal -> Year.of(animal.getBirthDate().getYear()).isLeap())
                        .map(animal -> Map.entry(entry.getKey() + " " + animal.getName(), animal.getBirthDate())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> existing));

        try {
            objectMapper.writeValue(file, outputMap);

//            Map<String, LocalDate> deserializedMap = objectMapper.readValue(file, Map.class);
//            for (Map.Entry<String, LocalDate> entry: deserializedMap.entrySet()) {
//                System.out.println(entry.getKey() + " " + entry.getValue());
//            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return outputMap;
    }

    @Override
    public Map<Animal, Short> findOlderAnimal(int N) {
        Path path = Paths.get("src/main/resources/results/findOlderAnimal.json");
        File file = new File(path.toString());

        if (N >= 0) {
            Animal oldestAnimal = animalStorage.entrySet().stream()
                    .filter(entry -> entry.getKey() != null)
                    .flatMap(entry -> entry.getValue().stream()).min((animal1, animal2) -> {
                        if (animal1.getBirthDate().isBefore(animal2.getBirthDate()))
                            return -1;
                        else if (animal1.getBirthDate().equals(animal2.getBirthDate()))
                            return 0;
                        else
                            return 1;
                    })
                    .orElse(null);

            Map<Animal, Short> olderAnimals = animalStorage.entrySet().stream()
                    .filter(entry -> entry.getKey() != null)
                    .map(Map.Entry::getValue)
                    .flatMap(List::stream)
                    .filter(animal -> animal.getAge() > N)
                    .collect(Collectors.toMap(animal -> animal, Animal::getAge));

            if (olderAnimals.isEmpty() && oldestAnimal != null)
                olderAnimals.put(oldestAnimal, oldestAnimal.getAge());


            try {
                objectMapper.writeValue(file, olderAnimals);

//                TypeReference<HashMap<Animal, Integer>> typeRef = new TypeReference<>() {};
//                Map<Animal,Integer> deserializedMap = objectMapper.readValue(file, typeRef);
//
////                Map<Animal, Integer> deserializedMap = objectMapper.readValue(file, Map.class);
//                for (Map.Entry<Animal, Integer> entry: deserializedMap.entrySet()) {
//                    System.out.println(entry.getKey() + " " + entry.getValue());
//                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return olderAnimals;
        } else {
            throw new NegativeArgumentException("Age can't be a negative");
        }
    }

    @Override
    public Map<String, List<Animal>> findDuplicate() {
        Path path = Paths.get("src/main/resources/results/findDuplicate.json");
        File file = new File(path.toString());

        Map<String, List<Animal>> duplicateMap = animalStorage.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .flatMap(entry -> entry.getValue().stream()
                        .filter(animal -> Collections.frequency(entry.getValue(), animal) > 1))
                .collect(Collectors.groupingBy(animal -> animal.getClass().toString(), Collectors.mapping(animal -> animal, Collectors.toList())));

        try {
            objectMapper.writeValue(file, duplicateMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return duplicateMap;
    }

    public double findAverageAge() {
        Path path = Paths.get("src/main/resources/results/findAverageAge.json");
        File file = new File(path.toString());

        double averageAge = animalStorage.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .flatMap(entry -> entry.getValue().stream())
                .mapToInt(Animal::getAge)
                .average()
                .orElse(0);

        BigDecimal result = new BigDecimal(averageAge).setScale(2, RoundingMode.HALF_UP);
        try {
            objectMapper.writeValue(file, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result.doubleValue();
    }

    @Override
    public List<Animal> findOldAndExpensive() {
        Path path = Paths.get("src/main/resources/results/findOldAndExpensive.json");
        File file = new File(path.toString());

        double averageCost = animalStorage.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .flatMap(entry -> entry.getValue().stream())
                .mapToDouble(animal -> animal.getCost().doubleValue())
                .average()
                .orElse(0);

        List<Animal> oldAndExpensiveList = animalStorage.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .flatMap(entry -> entry.getValue().stream())
                .filter(animal -> animal.getCost().doubleValue() > averageCost &&
                        Period.between(animal.getBirthDate(), LocalDate.now()).getYears() > 5)
                .sorted((animal1, animal2) -> {
                    if (animal1.getBirthDate().isBefore(animal2.getBirthDate()))
                        return -1;
                    else if (animal1.getBirthDate().equals(animal2.getBirthDate()))
                        return 0;
                    else
                        return 1;
                })
                .toList();

        try {
            objectMapper.writeValue(file, oldAndExpensiveList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return oldAndExpensiveList;
    }

    @Override
    public List<String> findMinCostAnimals() throws IllegalCollectionSizeException {
        Path path = Paths.get("src/main/resources/results/findMinCostAnimals.json");
        File file = new File(path.toString());

        long numOfAnimals = animalStorage.values().stream()
                .mapToLong(List::size)
                .sum();

        List<String> minCostAnimalsList = animalStorage.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .flatMap(entry -> entry.getValue().stream())
                .sorted(Comparator.comparingDouble(animal -> animal.getCost().doubleValue()))
                .map(Animal::getName)
                .limit(3)
                .sorted(Comparator.reverseOrder())
                .toList();

        try {
            objectMapper.writeValue(file, minCostAnimalsList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (numOfAnimals >= 3) {
            return minCostAnimalsList;
        } else {
            throw new IllegalCollectionSizeException("Low number of animals; Expected: 3, actual: ", numOfAnimals);
        }
    }
}
