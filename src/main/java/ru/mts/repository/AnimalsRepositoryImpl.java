package ru.mts.repository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import ru.mts.model.Animal;
import ru.mts.model.AnimalEnum;

import jakarta.annotation.PostConstruct;
import ru.mts.service.CreateAnimalServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Repository
public class AnimalsRepositoryImpl implements AnimalsRepository {
    // "хранилище" животных
    private Animal[] animalsArray;
    // объект для внедрения зависимостей
    private CreateAnimalServiceImpl createAnimalService;

    public AnimalsRepositoryImpl(CreateAnimalServiceImpl createAnimalService) {
        this.createAnimalService = createAnimalService;
    }

    /**
     * PostConstruct-метод для наполнения "хранилища"
     *
     * @author Nikita
     * @since 1.1
     */
    @PostConstruct
    public void fillStorage() {
        // инициализация "хранилища" животных
        animalsArray = createAnimalService.getAnimalsArray();

        // логгирование списка типов животных
        System.out.println("Типы животных:");
        for (AnimalEnum type : createAnimalService.getAnimalType()) {
            System.out.println(type);
        }
        System.out.println();
    }

    @Override
    public String[] findLeapYearNames() {
        // массив для имён животных, родившихся в високосных год
        String[] outputArray = new String[animalsArray.length];
        // счётчик
        int counter = 0;

        for (Animal animal : animalsArray) {
            // год рождения животного
            int year = animal.getBirthDate().getYear();
            // проверка года на високосность
            if (year % 4 == 0 && ((year % 100 != 0) || (year % 400 == 0))) {
                outputArray[counter] = animal.getName();
                counter++;
            }
        }

        return Arrays.copyOf(outputArray, counter);
    }

    @Override
    public Animal[] findOlderAnimal(int N) {
        // массив для животных, возраст которых больше N лет
        Animal[] outputArray = new Animal[animalsArray.length];
        // счётчик
        int counter = 0;
        // возраст
        int age;

        for (Animal animal : animalsArray) {
            age = LocalDate.now().getYear() - animal.getBirthDate().getYear();

            // корректировка возраста
            if (LocalDate.now().getDayOfYear() < animal.getBirthDate().getDayOfYear())
                age -= 1;

            if (age > N) {
                outputArray[counter] = animal;
                counter++;
            }
        }

        return Arrays.copyOf(outputArray, counter);
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

        System.out.println("Дубликаты: ");
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
    public Set<Animal> findDuplicate() {
        // множество дубликатов животных
        Set<Animal> duplicates = new HashSet<>();
        // флаг
        boolean flag;

        for (int i = 0; i < animalsArray.length - 1; i++) {
            for (int j = i + 1; j < animalsArray.length; j++) {
                // проверка равенства объектов
                if (animalsArray[i].equals(animalsArray[j])) {
                    flag = false;
                    // проверка наличия найденного дубликата в массиве дубликатов
                    for (Animal animal : duplicates) {
                        if (animal != null && animal.equals(animalsArray[i])) {
                            flag = true;
                            break;
                        }
                    }

                    // если найденного дубликата в массиве дубликатов нет, то добавляемя его туда
                    if (!flag) {
                        duplicates.add(animalsArray[i]);
                    }
                }
            }
        }

        // вывод дубликатов
        printDuplicate(duplicates);

        return duplicates;
    }
}
