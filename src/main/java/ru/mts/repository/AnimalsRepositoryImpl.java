package ru.mts.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.mts.model.Animal;
import ru.mts.model.AnimalEnum;
import ru.mts.service.CreateAnimalService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class AnimalsRepositoryImpl implements AnimalsRepository {
    // "хранилище" животных
    private Map<String, List<Animal>> animalsMap;
    // объект для внедрения зависимостей
    private CreateAnimalService createAnimalService;

    public AnimalsRepositoryImpl(CreateAnimalService createAnimalService) {
        this.createAnimalService = createAnimalService;
    }

    @Override
    public Map<String, List<Animal>> getAnimalsMap() {
        return animalsMap;
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
        // инициализация "хранилища" животных
        animalsMap = createAnimalService.receiveCreatedAnimals();

        // логгирование списка типов животных
        for (AnimalEnum type : createAnimalService.receiveAnimalType()) {
            System.out.println(type);
        }
        System.out.println();
    }

    @Override
    public Map<String, LocalDate> findLeapYearNames() {
        // массив для имён животных, родившихся в високосных год
        Map<String, LocalDate> output = new HashMap<>();

        for(Map.Entry<String, List<Animal>> node: createAnimalService.receiveCreatedAnimals().entrySet()) {
            if (node.getKey() != null) {
                for (Animal animal: node.getValue()) {
                    // год рождения животного
                    int year = animal.getBirthDate().getYear();
                    // проверка года на високосность
                    if (year % 4 == 0 && ((year % 100 != 0) || (year % 400 == 0))) {
                        output.put(node.getKey() + " " +animal.getName(), animal.getBirthDate());
                    }
                }
            }
            else {
                throw new IllegalStateException("Invalid key: " + null);
            }
        }

        return output;
    }

    @Override
    public Map<Animal, Integer> findOlderAnimal(int N) {
        // массив для животных, возраст которых больше N лет
        Map<Animal, Integer> output = new HashMap<>();
        boolean flag = false;
        // возраст
        int age;
        // счётчик
        int maxAge = 0;
        Animal olderAnimal = null;

        for (Map.Entry<String, List<Animal>> node: createAnimalService.receiveCreatedAnimals().entrySet()) {
            if (node.getKey() != null) {
                for (Animal animal : node.getValue()) {
                    age = LocalDate.now().getYear() - animal.getBirthDate().getYear();

                    // корректировка возраста
                    if (LocalDate.now().getDayOfYear() < animal.getBirthDate().getDayOfYear())
                        age -= 1;

                    if (!flag) {
                        if (age > maxAge) {
                            olderAnimal = animal;
                            maxAge = age;
                        }
                    }

                    if (age > N) {
                        flag = true;
                        output.put(animal, age);
                    }
                }
            }
            else {
                throw new IllegalStateException("Invalid key: " + null);
            }
        }

        if (!flag) {
            output.put(olderAnimal, maxAge);
        }

        return output;
    }

    /**
     * Метод выводит на экран множество дубликатов животных.
     * Метод может быть вызван только из метода findDuplicate().
     *
     * @param dupl множество дубликатов
     * @author Nikita
     * @since 1.2
     */
    private void printDuplicate(Set<Animal> dupl) {
        // формат даты
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // счётчик
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
        // множество дубликатов животных
        Map<String, Integer> duplicates = new HashMap<>(4);
        duplicates.put("Cat", 0);
        duplicates.put("Dog", 0);
        duplicates.put("Wolf", 0);
        duplicates.put("Shark", 0);

        Map<String, Integer> output = new HashMap<>();
        // флаг
        boolean flag;
        // множество дубликатов животных
        Set<Animal> duplSet = new HashSet<>();

        for (Map.Entry<String, List<Animal>> node: createAnimalService.receiveCreatedAnimals().entrySet()) {
            if (node.getKey() != null && node.getValue() != null) {
                for (int i = 0; i < node.getValue().size() - 1; i++) {
                    for (int j = i + 1; j < node.getValue().size(); j++) {
                        if (node.getValue().get(i) != null || node.getValue().get(j) != null) {
                            if (node.getValue().get(i).equals(node.getValue().get(j))) {
                                flag = false;
                                // проверка наличия найденного дубликата в массиве дубликатов
                                for (Animal animal : duplSet) {
                                    if (animal != null && animal.equals(node.getValue().get(i))) {
                                        flag = true;
                                        break;
                                    }
                                }
                                // если найденного дубликата в массиве дубликатов нет, то добавляемя его туда
                                if (!flag) {
                                    duplSet.add(node.getValue().get(i));
                                }
                                duplicates.put(node.getKey(), duplicates.get(node.getKey()) + 1);
                                break;
                            }
                        } else {
                            throw new IllegalStateException("Invalid value of Animal: " + null);
                        }
                    }
                }
            }
            else {
                throw new IllegalStateException("Invalid value: " + null);
            }
        }

        // вывод дубликатов
        printDuplicate(duplSet);

        return duplicates;
    }
}
