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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Scope("prototype")
@EnableConfigurationProperties(AnimalsProperties.class)
public class CreateAnimalServiceImpl implements CreateAnimalService {
    // список типов созданных животных
    private List<AnimalEnum> animalType;
    // список животных
    private Animal[] animalsArray;

    private AnimalsProperties animalsProperties;

    public CreateAnimalServiceImpl(AnimalsProperties animalsProperties) {
        this.animalsProperties = animalsProperties;
    }

    @Override
    public List<AnimalEnum> receiveAnimalType() {
        return animalType;
    }

    public Animal[] receiveAnimalsArray() {
        return animalsArray;
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
        animalsArray = createAnimals();

        System.out.println("define type");

        // инициализация списка типов животных
        animalType = new ArrayList<>(animalsArray.length);
        for (Animal animal : animalsArray) {
            switch (animal.getClass().toString()) {
                case "class ru.mts.model.Cat":
                    animalType.add(AnimalEnum.CAT);
                    break;
                case "class ru.mts.model.Dog":
                    animalType.add(AnimalEnum.DOG);
                    break;
                case "class ru.mts.model.Wolf":
                    animalType.add(AnimalEnum.WOLF);
                    break;
                case "class ru.mts.model.Shark":
                    animalType.add(AnimalEnum.SHARK);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + animal.getClass().toString());
            }
        }
    }

    @Override
    public Animal[] createAnimals() {
        // счётчик
        int counter = 1;
        // инициализация массива животных
        animalsArray = new Animal[10];

        do {
            animalsArray[counter - 1] = commonCreating(counter);
        }
        while (counter++ < 10);

        Animal[] returnArray = Arrays.copyOf(animalsArray, animalsArray.length);
        return returnArray;
    }


    /**
     * Перегруженный метод для создания животных при помощи цикла for.
     * Аргументы: N - количество животных, которое необходимо создать
     *
     * @return массив созданных животных
     * @since 1.1
     * @author Nikita
     */
    public Animal[] createAnimals(int N) {
        // массив животных
        animalsArray = new Animal[N];
        for (int i = 0; i < N; i++) {
            animalsArray[i] = commonCreating(i + 1);
        }
        Animal[] returnArray = Arrays.copyOf(animalsArray, animalsArray.length);
        return returnArray;
    }
}
