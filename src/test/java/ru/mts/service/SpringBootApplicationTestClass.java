package ru.mts.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import ru.mts.AnimalsProperties;
import ru.mts.model.*;
import ru.mts.repository.AnimalsRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Class for testing application")
@SpringBootTest
@Profile("test")
public class SpringBootApplicationTestClass {
    Animal cat, dog, wolf, shark, anotherCat, anotherDog, sameCat;
    @MockBean
    private CreateAnimalService createAnimalService;
    @Autowired
    private AnimalsRepository animalsRepository;
    @Autowired
    private AnimalsProperties animalsProperties;

    // см. описание в TestClass
    public static boolean containsArray(Object[][] mainArray, Object[] subArray) {
        for (Object[] row : mainArray) {
            if (Arrays.deepEquals(row, subArray)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод для инициализации животных
     *
     * @author Nikita
     * @since 1.4
     */
    private void initAnimals() {
        cat = new Cat("Британская", animalsProperties.getCatNames().get(1), BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.now().minusYears(10).minusDays(1));
        dog = new Dog("Доберман", animalsProperties.getDogNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.now().minusYears(10));
        wolf = new Wolf("Японский", animalsProperties.getWolfNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.now().minusYears(10).plusDays(1));
        shark = new Shark("Молот", animalsProperties.getSharkNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1996, 6, 13));
        anotherCat = new Cat("Сфинкс", animalsProperties.getCatNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2004, 1, 5));
        anotherDog = new Dog("Немецкая овчарка", animalsProperties.getDogNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(15000).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(1984, 10, 12));
        sameCat = new Cat("Британская", animalsProperties.getCatNames().get(1), BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.now().minusYears(10).minusDays(1));
    }

    @DisplayName("Test findLeapYearNames method")
    @ParameterizedTest(name = "Test {arguments}")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    public void findLeapYearNames(int value) {
        initAnimals();
        // массив животных, который якобы заполнил CreateAnimalServcieImpl
        Animal[] inputArray;
        // список типов животных, который якобы заполнил CreateAnimalServcieImpl
        List<AnimalEnum> types;
        // массив имён животных, родившихся в високосный год
        String[] outputArray;

        switch (value) {
            case 0:
                // случай, когда в массиве есть животные, родившиеся в високосный год
                inputArray = new Animal[]{cat, dog, wolf, shark, anotherCat, anotherDog, sameCat};
                types = List.of(AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.SHARK, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.CAT);
                outputArray = new String[]{shark.getName(), anotherCat.getName(), anotherDog.getName()};
                break;
            case 1:
                // случай когда в массиве нет животных, родившихся в високосный год
                inputArray = new Animal[]{cat, wolf, dog};
                types = List.of(AnimalEnum.CAT, AnimalEnum.WOLF, AnimalEnum.DOG);
                outputArray = new String[0];
                break;
            case 2:
                // случай, когда массив животных пустой
                inputArray = new Animal[0];
                types = new ArrayList<>();
                outputArray = new String[0];
                break;
            case 3:
                // случай, когда в массиве животных есть null-значения
                inputArray = new Animal[]{wolf, dog, null, anotherCat};
                types = List.of(AnimalEnum.WOLF, AnimalEnum.DOG, AnimalEnum.CAT);
                outputArray = new String[0];
                break;
            case 4:
                // случай, когда массив равен null
                inputArray = null;
                types = null;
                outputArray = null;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }

        // задание поведения для MockBean
        when(createAnimalService.receiveAnimalsArray()).thenReturn(inputArray);
        when(createAnimalService.receiveAnimalType()).thenReturn(types);

        if (value == 4) {
            assertThrows(NullPointerException.class, () -> {
                animalsRepository.fillStorage();
            });
        } else {
            animalsRepository.fillStorage();
            if (value == 3) {
                assertThrows(NullPointerException.class, () -> {
                    animalsRepository.findLeapYearNames();
                });
            } else {
                assertArrayEquals(animalsRepository.findLeapYearNames(), outputArray);
            }
        }
    }

    @DisplayName("Test findOlderAnimal method")
    @ParameterizedTest(name = "Array of animals, more than {arguments} y.o.")
    @ValueSource(ints = {10, 9, 15, 50, 1, 35, 24, 12, 18})
    public void findOlderAnimal(int value) {
        initAnimals();
        // массив животных, который якобы заполнил CreateAnimalServcieImpl
        Animal[] inputArray = new Animal[]{cat, dog, wolf, shark, anotherCat, anotherDog, sameCat};
        // список типов животных, который якобы заполнил CreateAnimalServcieImpl
        List<AnimalEnum> types = List.of(AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.SHARK, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.CAT);
        // двумерный массив имён животных, возраст которых больше value
        Animal[][] outputArrays = {
                {cat, dog, shark, anotherCat, anotherDog, sameCat},
                {shark, anotherCat, anotherDog, sameCat},
                {shark, anotherCat, anotherDog},
                {cat, dog, wolf, shark, anotherCat, anotherDog, sameCat},
                {anotherDog},
                {}
        };

        switch (value) {
            case 24:
                // задаём поведение для MockBean, если массив животных пустой
                when(createAnimalService.receiveAnimalsArray()).thenReturn(new Animal[0]);
                when(createAnimalService.receiveAnimalType()).thenReturn(new ArrayList<>());
                break;
            case 12:
                // задаём поведение для MockBean, если массив животных содержит null-значения
                when(createAnimalService.receiveAnimalsArray()).thenReturn(new Animal[]{cat, dog, null});
                when(createAnimalService.receiveAnimalType()).thenReturn(List.of(AnimalEnum.CAT, AnimalEnum.DOG));
                break;
            case 18:
                // задаём поведение для MockBean, если массив животных равен null
                when(createAnimalService.receiveAnimalsArray()).thenReturn(null);
                when(createAnimalService.receiveAnimalType()).thenReturn(null);
                break;
            default:
                // задаём поведение для корректного случая
                when(createAnimalService.receiveAnimalsArray()).thenReturn(inputArray);
                when(createAnimalService.receiveAnimalType()).thenReturn(types);
                break;
        }

        if (value == 18) {
            assertThrows(NullPointerException.class, () -> {
                animalsRepository.fillStorage();
            });
        } else {
            animalsRepository.fillStorage();
            if (value == 12) {
                assertThrows(NullPointerException.class, () -> {
                    animalsRepository.findOlderAnimal(value);
                });
            } else {
                assertTrue(containsArray(outputArrays, animalsRepository.findOlderAnimal(value)));
            }
        }
    }

    @DisplayName("Test findDuplicate method")
    @ParameterizedTest(name = "Test {arguments}")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    public void findDuplicate(int value) {
        initAnimals();
        // массив животных, который якобы заполнил CreateAnimalServcieImpl
        Animal[] inputArray;
        // список типов животных, который якобы заполнил CreateAnimalServcieImpl
        List<AnimalEnum> types;
        // множество дубликатов животных
        Set<Animal> outputArray;

        switch (value) {
            case 0:
                // случай, когда в массиве животных есть дубликаты
                inputArray = new Animal[]{cat, dog, wolf, dog, shark, anotherCat, cat, anotherDog, sameCat};
                types = List.of(AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.DOG, AnimalEnum.SHARK, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.CAT);
                outputArray = new HashSet<>(Arrays.asList(dog, cat));
                break;
            case 1:
                // случай, когда в массиве животных нет дубликатов
                inputArray = new Animal[]{cat, wolf, anotherDog, shark};
                types = List.of(AnimalEnum.CAT, AnimalEnum.WOLF, AnimalEnum.CAT, AnimalEnum.SHARK);
                outputArray = new HashSet<>();
                break;
            case 2:
                // случай, когда массив животных пуст
                inputArray = new Animal[0];
                types = new ArrayList<>();
                outputArray = new HashSet<>();
                break;
            case 3:
                // случай, когда в массиве животных есть null-значения
                inputArray = new Animal[]{wolf, dog, null, anotherCat};
                types = List.of(AnimalEnum.WOLF, AnimalEnum.DOG, AnimalEnum.CAT);
                outputArray = new HashSet<>();
                break;
            case 4:
                // случай, когда массив животных равен null
                inputArray = null;
                types = null;
                outputArray = null;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }

        // задание поведения для MockBean
        when(createAnimalService.receiveAnimalsArray()).thenReturn(inputArray);
        when(createAnimalService.receiveAnimalType()).thenReturn(types);

        if (value == 4) {
            assertThrows(NullPointerException.class, () -> {
                animalsRepository.fillStorage();
            });
        } else {
            animalsRepository.fillStorage();
            if (value == 3) {
                assertThrows(NullPointerException.class, () -> {
                    animalsRepository.findDuplicate();
                });
            } else {
                assertEquals(animalsRepository.findDuplicate(), outputArray);
            }
        }
    }
}
