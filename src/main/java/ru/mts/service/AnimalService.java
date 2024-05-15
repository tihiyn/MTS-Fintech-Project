package ru.mts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.mts.exceptions.IllegalCollectionSizeException;
import ru.mts.exceptions.NegativeArgumentException;
import ru.mts.model.Animal;
import ru.mts.repository.AnimalRepository;
import ru.mts.util.DBService;

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

@Service
@Scope("prototype")
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final ObjectMapper objectMapper;

    public void setAnimalStorage(ConcurrentMap<String, List<Animal>> animalStorage) {
        this.animalStorage = animalStorage;
    }

    private ConcurrentMap<String, List<Animal>> animalStorage;

    @PostConstruct
    public void fillStorage() {
        animalStorage = animalRepository.findAll().stream()
                .collect(Collectors.groupingByConcurrent(animal -> animal.getAnimalType().getType()));
    }

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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return outputMap;
    }

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

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return olderAnimals;
        } else {
            throw new NegativeArgumentException("Age can't be a negative");
        }
    }

    public Map<String, List<Animal>> findDuplicate() {
        Path path = Paths.get("src/main/resources/results/findDuplicate.json");
        File file = new File(path.toString());

        Map<String, List<Animal>> duplicateMap = animalStorage.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .flatMap(entry -> entry.getValue().stream()
                        .filter(animal -> Collections.frequency(entry.getValue(), animal) > 1))
                .collect(Collectors.groupingBy(animal -> animal.getAnimalType().getType(), Collectors.mapping(animal -> animal, Collectors.toList())));

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

    public void saveAnimals(List<Animal> animals) {
        animalRepository.saveAll(animals);
    }

    public void deleteAnimals() {
        animalRepository.deleteAll();
    }
}
