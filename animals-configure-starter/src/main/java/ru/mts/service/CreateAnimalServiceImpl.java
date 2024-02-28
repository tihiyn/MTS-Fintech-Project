package ru.mts.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mts.AnimalsProperties;
import ru.mts.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Scope("prototype")
@EnableConfigurationProperties(AnimalsProperties.class)
public class CreateAnimalServiceImpl implements CreateAnimalService {
    // список типов созданных животных
    private List<AnimalEnum> animalType;
    // созданные животные
    private Map<String, List<Animal>> createdAnimals;

    private AnimalsProperties animalsProperties;

    public CreateAnimalServiceImpl(AnimalsProperties animalsProperties) {
        this.animalsProperties = animalsProperties;
    }

    @Override
    public List<AnimalEnum> receiveAnimalType() {
        return animalType;
    }

    public Map<String, List<Animal>> receiveCreatedAnimals() {
        return createdAnimals;
    }

    /**
     * Метод, который генерирует случайную дату.
     *
     * @return случайная дата.
     * @author Nikita
     * @since 1.1
     */
    private LocalDate createRandomDate() {
        // случайный год
        int year = ThreadLocalRandom.current().nextInt(2023 - 1970 + 1) + 1970;
        // случайный месяц
        int month = ThreadLocalRandom.current().nextInt(12) + 1;
        // количество дней в месяце
        int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        // случайный день
        int day = ThreadLocalRandom.current().nextInt(daysInMonth) + 1;

        return LocalDate.of(year, month, day);
    }

    /**
     * Метод, отвечающий непосредственно за создание животного.
     *
     * @return объект животного.
     * @author Nikita
     * @since 1.1
     */
    private Animal commonCreating(int counter) {
        // переменная класса ru.mts.model.Animal
        Animal animal;
        // формат даты
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // сгенерируем случайное число от 0 до 3, которое будет сопоставлено с одним из классов животных
        int numOfClass = ThreadLocalRandom.current().nextInt(4);
        // сгенерируем случайную цену
        BigDecimal randCost = new BigDecimal(ThreadLocalRandom.current().nextDouble(10000, 500000)).setScale(2, RoundingMode.HALF_UP);

        // создание животного
        animal = switch (numOfClass) {
            case 0 ->
                    new Cat(breeds[ThreadLocalRandom.current().nextInt(3)], animalsProperties.getCatNames().get(ThreadLocalRandom.current().nextInt(3)), randCost, characters[ThreadLocalRandom.current().nextInt(6)], createRandomDate());
            case 1 ->
                    new Dog(breeds[ThreadLocalRandom.current().nextInt(3, 6)], animalsProperties.getDogNames().get(ThreadLocalRandom.current().nextInt(3)), randCost, characters[ThreadLocalRandom.current().nextInt(6)], createRandomDate());
            case 2 ->
                    new Shark(breeds[ThreadLocalRandom.current().nextInt(6, 9)], animalsProperties.getSharkNames().get(ThreadLocalRandom.current().nextInt(3)), randCost, characters[ThreadLocalRandom.current().nextInt(6)], createRandomDate());
            case 3 ->
                    new Wolf(breeds[ThreadLocalRandom.current().nextInt(9, 12)], animalsProperties.getWolfNames().get(ThreadLocalRandom.current().nextInt(3)), randCost, characters[ThreadLocalRandom.current().nextInt(6)], createRandomDate());
            default -> null;
        };
        // логирование
        System.out.format("%d-ое животное: %s\n", counter, animal.getClass().getName());
        System.out.println("Порода: " + animal.getBreed());
        System.out.println("Кличка: " + animal.getName());
        System.out.println("Цена: " + animal.getCost());
        System.out.println("Характер: " + animal.getCharacter());
        System.out.println("Голос: " + animal.getVoice());
        System.out.println("День рождения животного: " + animal.getBirthDate().format(formatter));
        System.out.println();

        return animal;
    }

    @Override
    public void defineTypeOfAnimals() {
        // вызов метода для создания животных
        createdAnimals = createAnimals();

        // инициализация списка типов животных
        animalType = new ArrayList<>();

        for (Map.Entry<String, List<Animal>> node : createdAnimals.entrySet()) {
            switch (node.getKey()) {
                case "Cat":
                    for (int i = 0; i < node.getValue().size(); i++) {
                        if (node.getValue().get(i) != null)
                            animalType.add(AnimalEnum.CAT);
                        else
                            animalType.add(null);
                    }
                    break;
                case "Dog":
                    for (int i = 0; i < node.getValue().size(); i++) {
                        if (node.getValue().get(i) != null)
                            animalType.add(AnimalEnum.DOG);
                        else
                            animalType.add(null);
                    }
                    break;
                case "Wolf":
                    for (int i = 0; i < node.getValue().size(); i++) {
                        if (node.getValue().get(i) != null)
                            animalType.add(AnimalEnum.WOLF);
                        else
                            animalType.add(null);
                    }
                    break;
                case "Shark":
                    for (int i = 0; i < node.getValue().size(); i++) {
                        if (node.getValue().get(i) != null)
                            animalType.add(AnimalEnum.SHARK);
                        else
                            animalType.add(null);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + node.getKey());
            }
        }
    }

    @Override
    public Map<String, List<Animal>> createAnimals() {
        // счётчик
        int counter = 1;
        // создание "макета" мапы
        createdAnimals = new HashMap<>(4);
        createdAnimals.put("Cat", new ArrayList<>());
        createdAnimals.put("Dog", new ArrayList<>());
        createdAnimals.put("Wolf", new ArrayList<>());
        createdAnimals.put("Shark", new ArrayList<>());

        do {
            Animal animal = commonCreating(counter);
            switch (animal.getClass().toString()) {
                case "class ru.mts.model.Cat":
                    createdAnimals.get("Cat").add(animal);
                    break;
                case "class ru.mts.model.Dog":
                    createdAnimals.get("Dog").add(animal);
                    break;
                case "class ru.mts.model.Wolf":
                    createdAnimals.get("Wolf").add(animal);
                    break;
                case "class ru.mts.model.Shark":
                    createdAnimals.get("Shark").add(animal);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + animal.getClass().toString());
            }
        }
        while (counter++ < 10);

        return createdAnimals;
    }


    /**
     * Перегруженный метод для создания животных при помощи цикла for.
     * Аргументы: N - количество животных, которое необходимо создать
     *
     * @return массив созданных животных
     * @author Nikita
     * @since 1.1
     */
    public Map<String, List<Animal>> createAnimals(int N) {
        // создание "макета" мапы
        createdAnimals = new HashMap<>(4);
        createdAnimals.put("Cat", new ArrayList<>());
        createdAnimals.put("Dog", new ArrayList<>());
        createdAnimals.put("Wolf", new ArrayList<>());
        createdAnimals.put("Shark", new ArrayList<>());
        for (int i = 0; i < N; i++) {
            Animal animal = commonCreating(i + 1);
            switch (animal.getClass().toString()) {
                case "class ru.mts.model.Cat":
                    createdAnimals.get("Cat").add(animal);
                    break;
                case "class ru.mts.model.Dog":
                    createdAnimals.get("Dog").add(animal);
                    break;
                case "class ru.mts.model.Wolf":
                    createdAnimals.get("Wolf").add(animal);
                    break;
                case "class ru.mts.model.Shark":
                    createdAnimals.get("Shark").add(animal);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + animal.getClass().toString());
            }
        }

        return createdAnimals;
    }
}
