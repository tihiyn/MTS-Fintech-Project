package ru.mts.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.mts.model.Animal;
import ru.mts.model.AnimalEnum;
import ru.mts.service.CreateAnimalService;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class AnimalsRepositoryImpl implements AnimalsRepository {
    private Map<AnimalEnum, List<Animal>> animalStorage;

    private CreateAnimalService createAnimalService;

    public AnimalsRepositoryImpl(CreateAnimalService createAnimalService) {
        this.createAnimalService = createAnimalService;
    }

    @Override
    public Map<AnimalEnum, List<Animal>> getAnimalStorage() {
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
        animalStorage = createAnimalService.receiveCreatedAnimals();

        for (AnimalEnum type : createAnimalService.receiveAnimalTypes()) {
            System.out.println(type);
        }
        System.out.println();
    }

    @Override
    public Map<String, LocalDate> findLeapYearNames() {
        Map<String, LocalDate> leapYearAnimals = new HashMap<>();

        for (Map.Entry<AnimalEnum, List<Animal>> node : animalStorage.entrySet()) {
            if (node.getKey() != null) {
                for (Animal animal : node.getValue()) {
                    int year = animal.getBirthDate().getYear();

                    if (year % 4 == 0 && ((year % 100 != 0) || (year % 400 == 0))) {
                        leapYearAnimals.put(node.getKey().toString() + " " + animal.getName(), animal.getBirthDate());
                    }
                }
            } else {
                throw new IllegalStateException("Invalid key: " + null);
            }
        }

        return leapYearAnimals;
    }

    @Override
    public Map<Animal, Integer> findOlderAnimal(int N) {
        Map<Animal, Integer> olderThanNAnimals = new HashMap<>();

        boolean flag = false;
        int maxAge = 0;
        Animal oldestAnimal = null;

        for (Map.Entry<AnimalEnum, List<Animal>> node : animalStorage.entrySet()) {
            if (node.getKey() != null) {
                for (Animal animal : node.getValue()) {
                    int age = Period.between(animal.getBirthDate(), LocalDate.now()).getYears();

                    if (!flag) {
                        if (oldestAnimal == null)
                            oldestAnimal = animal;

                        if (animal.getBirthDate().isBefore(oldestAnimal.getBirthDate())) {
                            oldestAnimal = animal;
                            maxAge = age;
                        }
                    }

                    if (age > N) {
                        flag = true;
                        olderThanNAnimals.put(animal, age);
                    }
                }
            } else {
                throw new IllegalStateException("Invalid key: " + null);
            }
        }

        if (!flag) {
            olderThanNAnimals.put(oldestAnimal, maxAge);
        }

        return olderThanNAnimals;
    }

    /**
     * Метод выводит на экран множество дубликатов животных.
     * Метод может быть вызван только из метода findDuplicate().
     *
     * @param dupl множество дубликатов
     * @author Nikita
     * @since 1.2
     */
    private void printSetOfDuplicates(Set<Animal> dupl) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int counter = 0;

        System.out.println("Animal duplicates: ");
        for (Animal animal : dupl) {
            System.out.format("%d-ый дубликат: %s\n", counter + 1, animal.getClass().getName());
            System.out.println("Порода: " + animal.getBreed());
            System.out.println("Кличка: " + animal.getName());
            System.out.println("Цена: " + animal.getCost());
            System.out.println("Характер: " + animal.getCharacter());
            System.out.println("Голос: " + animal.getVoice());
            System.out.println("День рождения животного: " + animal.getBirthDate().format(formatter));
            System.out.println();
            counter++;
        }
    }

    @Override
    public Map<String, Integer> findDuplicate() {
        Map<String, Integer> duplicates = new HashMap<>();
        Set<Animal> setOfDuplicates = new HashSet<>();
        boolean flag;

        for (AnimalEnum animalEnum : AnimalEnum.values()) {
            duplicates.put(animalEnum.toString(), 0);
        }

        for (Map.Entry<AnimalEnum, List<Animal>> node : animalStorage.entrySet()) {
            if (node.getValue() != null && node.getKey() != null) {
                for (int i = 0; i < node.getValue().size() - 1; i++) {
                    for (int j = i + 1; j < node.getValue().size(); j++) {
                        if (node.getValue().get(i).equals(node.getValue().get(j))) {
                            flag = false;
                            for (Animal animal : setOfDuplicates) {
                                if (animal != null && animal.equals(node.getValue().get(i))) {
                                    flag = true;
                                    break;
                                }
                            }

                            if (!flag) {
                                setOfDuplicates.add(node.getValue().get(i));
                            }
                            duplicates.put(node.getKey().toString(), duplicates.get(node.getKey().toString()) + 1);
                            break;
                        }
                    }
                }
            } else {
                throw new IllegalStateException("Invalid value: " + null);
            }
        }

        printSetOfDuplicates(setOfDuplicates);

        return duplicates;
    }
}
