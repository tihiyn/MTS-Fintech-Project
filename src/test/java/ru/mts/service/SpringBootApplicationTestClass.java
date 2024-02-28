package ru.mts.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
public class SpringBootApplicationTestClass {
    Animal cat1, cat2, cat3, dog1, wolf1, wolf2, shark1, sameCat2, sameShark1, sameCat3;
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

    public static boolean containsHashMap(List<HashMap<Animal, Integer>> list, Map<Animal, Integer> targetMap) {
        for (HashMap<Animal, Integer> map : list) {
            if (map.equals(targetMap)) {
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
        cat1 = new Cat("Британская", animalsProperties.getCatNames().get(1), BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.now().minusYears(10).minusDays(1));
        cat2 = new Cat("Шотландская", animalsProperties.getCatNames().get(0), BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18));
        cat3 = new Cat("Сфинкс", animalsProperties.getCatNames().get(2), BigDecimal.valueOf(7000.2).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2008, 9, 9));

        dog1 = new Dog("Доберман", animalsProperties.getDogNames().get(2), BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.now().minusYears(10));

        wolf1 = new Wolf("Японский", animalsProperties.getWolfNames().get(0), BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.now().minusYears(10).plusDays(1));
        wolf2 = new Wolf("Полярный", animalsProperties.getWolfNames().get(1), BigDecimal.valueOf(700000.157).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.of(1997, 2, 1));

        shark1 = new Shark("Молот", animalsProperties.getSharkNames().get(1), BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1996, 6, 13));

        sameCat2 = new Cat("Шотландская", animalsProperties.getCatNames().get(0), BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18));
        sameShark1 = new Shark("Молот", animalsProperties.getSharkNames().get(1), BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1996, 6, 13));
        sameCat3 = new Cat("Сфинкс", animalsProperties.getCatNames().get(2), BigDecimal.valueOf(7000.2).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2008, 9, 9));

    }

    @DisplayName("Test findLeapYearNames method")
    @ParameterizedTest(name = "Test {arguments}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
    public void findLeapYearNames(int value) {
        initAnimals();
        // массив животных, который якобы заполнил CreateAnimalServcieImpl
        Map<String, List<Animal>> input = new HashMap<>();
        /*
        Список типов животных, который якобы заполнил CreateAnimalServcieImpl.
        На самом деле порядок элементов списка может быть не совсем таким, потому что
        элементы в HashMap не упорядочены.
         */
        List<AnimalEnum> types;
        // массив имён животных, родившихся в високосный год
        Map<String, LocalDate> output = new HashMap<>();

        switch (value) {
            case 0:
                // случай, когда в массиве есть животные, родившиеся в високосный год
                input.put("Cat", List.of(cat1, cat3, cat2));
                input.put("Dog", List.of(dog1));
                input.put("Wolf", List.of(wolf1, wolf2));
                input.put("Shark", List.of(shark1));
                types = List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF, AnimalEnum.SHARK);
                output.put("Cat " + cat3.getName(), LocalDate.of(2008, 9, 9));
                output.put("Shark " + shark1.getName(), LocalDate.of(1996, 6, 13));
                break;
            case 1:
                // случай когда в массиве нет животных, родившихся в високосный год
                input.put("Cat", List.of(cat1, cat2));
                input.put("Dog", List.of(dog1));
                input.put("Wolf", List.of(wolf1, wolf2));
                types = List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF);
                break;
            case 2:
                // вариант, когда список содержит null-значения
                input.put("Shark", List.of(shark1));
                input.put("Dog", new ArrayList<>());
                input.put("Wolf", Arrays.asList(wolf1, null));
                input.put("Cat", new ArrayList<>());
                types = Arrays.asList(AnimalEnum.SHARK, AnimalEnum.WOLF, null);
                break;
            case 3:
                // вариант, когда все списки пустые
                input.put("Cat", new ArrayList<>());
                input.put("Dog", new ArrayList<>());
                input.put("Wolf", new ArrayList<>());
                input.put("Shark", new ArrayList<>());
                types = new ArrayList<>();
                break;
            case 4:
                // вариант, когда value равно null
                input.put("Cat", null);
                input.put("Dog", List.of(dog1));
                types = List.of(AnimalEnum.DOG);
                break;
            case 5:
                // вариант, когда key равен null
                input.put(null, new ArrayList<>());
                input.put("Shark", List.of(shark1));
                types = List.of(AnimalEnum.SHARK);
                break;
            case 6:
                // вариант, когда мапа равна null
                input = null;
                types = new ArrayList<>();
                break;
            case 7:
                // вариант, когда мапа пустая
                types = new ArrayList<>();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }

        // задание поведения для MockBean
        when(createAnimalService.receiveCreatedAnimals()).thenReturn(input);
        when(createAnimalService.receiveAnimalType()).thenReturn(types);

        animalsRepository.fillStorage();
        if (value == 2 || value == 4 || value == 6) {
            assertThrows(NullPointerException.class, () -> {
                animalsRepository.findLeapYearNames();
            });
        }
        else if (value == 5) {
            assertThrows(IllegalStateException.class, () -> {
                animalsRepository.findLeapYearNames();
            });
        }
        else {
            assertEquals(output, animalsRepository.findLeapYearNames());
        }
    }

    @DisplayName("Test findOlderAnimal method")
    @ParameterizedTest(name = "Array of animals, more than {arguments} y.o.")
    @ValueSource(ints = {10, 9, 20, 50, 24, 12, 18, 5})
    public void findOlderAnimal(int value) {
        initAnimals();
        // массив животных, который якобы заполнил CreateAnimalServcieImpl
        Map<String, List<Animal>> input = new HashMap<>();
        input.put("Cat", List.of(cat1, cat3, cat2));
        input.put("Dog", List.of(dog1));
        input.put("Wolf", List.of(wolf1, wolf2));
        input.put("Shark", List.of(shark1));
        // список типов животных, который якобы заполнил CreateAnimalServcieImpl
        List<AnimalEnum> types = List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF, AnimalEnum.SHARK);
        // двумерный массив имён животных, возраст которых больше value
        List<HashMap<Animal, Integer>> outputResults = List.of(
                new HashMap<Animal, Integer>() {{
                    put(cat3, 15);
                    put(wolf2, 27);
                    put(shark1, 27);
                }},
                new HashMap<Animal, Integer>() {{
                    put(cat1, 10);
                    put(cat2, 10);
                    put(cat3, 15);
                    put(dog1, 10);
                    put(wolf2, 27);
                    put(shark1, 27);
                }},
                new HashMap<Animal, Integer>() {{
                    put(wolf2, 27);
                    put(shark1, 27);
                }},
                new HashMap<Animal, Integer>() {{
                    put(shark1, 27);
                }},
                new HashMap<Animal, Integer>() {{
                    put(null, 0);
                }}
        );

        switch (value) {
            case 24:
                // задаём поведение для MockBean, если массив животных пустой
                when(createAnimalService.receiveCreatedAnimals()).thenReturn(new HashMap<>() {{
                    put("Cat", Arrays.asList(cat1, null));
                    put("Dog", List.of(dog1));
                }});
                when(createAnimalService.receiveAnimalType()).thenReturn(Arrays.asList(AnimalEnum.CAT, null, AnimalEnum.DOG));
                break;
            case 12:
                // задаём поведение для MockBean, если массив животных содержит null-значения
                when(createAnimalService.receiveCreatedAnimals()).thenReturn(new HashMap<>() {{
                    put("Cat", new ArrayList<>());
                    put("Dog", new ArrayList<>());
                }});
                when(createAnimalService.receiveAnimalType()).thenReturn(new ArrayList<>());
                break;
            case 18:
                // задаём поведение для MockBean, если массив животных равен null
                when(createAnimalService.receiveCreatedAnimals()).thenReturn(null);
                when(createAnimalService.receiveAnimalType()).thenReturn(new ArrayList<>());
                break;
            case 5:
                // задаём поведение для MockBean, если массив животных содержит null-значения
                when(createAnimalService.receiveCreatedAnimals()).thenReturn(new HashMap<>() {{
                    put(null, new ArrayList<>(List.of(cat1, cat2)));
                    put("Dog", new ArrayList<>(List.of(dog1)));
                }});
                when(createAnimalService.receiveAnimalType()).thenReturn(List.of(AnimalEnum.DOG));
                break;
            default:
                // задаём поведение для корректного случая
                when(createAnimalService.receiveCreatedAnimals()).thenReturn(input);
                when(createAnimalService.receiveAnimalType()).thenReturn(types);
                break;
        }

        animalsRepository.fillStorage();
        if (value == 24 || value == 18) {
            assertThrows(NullPointerException.class, () -> {
                animalsRepository.findOlderAnimal(value);
            });
        } else if (value == 5) {
            assertThrows(IllegalStateException.class, () -> {
                animalsRepository.findOlderAnimal(value);
            });
        } else {
            assertTrue(containsHashMap(outputResults, animalsRepository.findOlderAnimal(value)));
        }
    }

    @DisplayName("Test findDuplicate method")
    @ParameterizedTest(name = "Test {arguments}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
    public void findDuplicate(int value) {
        initAnimals();
        // массив животных, который якобы заполнил CreateAnimalServcieImpl
        Map<String, List<Animal>> input = new HashMap<>();
        // список типов животных, который якобы заполнил CreateAnimalServcieImpl
        List<AnimalEnum> types;
        // множество дубликатов животных
        Map<String, Integer> output = new HashMap<>();

        switch (value) {
            case 0:
                // случай, когда в мапе животных есть дубликаты
                input.put("Cat", List.of(cat1, cat3, sameCat3, cat3, cat2, sameCat2, cat1));
                input.put("Dog", List.of(dog1));
                input.put("Wolf", List.of(wolf1, wolf2));
                input.put("Shark", List.of(shark1, sameShark1, shark1, sameShark1));
                types = List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT,
                        AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF,
                        AnimalEnum.SHARK, AnimalEnum.SHARK, AnimalEnum.SHARK, AnimalEnum.SHARK);
                output.put("Cat", 4);
                output.put("Shark", 3);
                output.put("Dog", 0);
                output.put("Wolf", 0);
                break;
            case 1:
                // другой случай, когда в мапе животных есть дубликаты
                input.put("Cat", List.of(cat1, cat3, cat2));
                input.put("Dog", List.of(dog1));
                input.put("Wolf", List.of(wolf1, wolf2));
                input.put("Shark", List.of(shark1, shark1, shark1));
                types = List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF,
                        AnimalEnum.WOLF, AnimalEnum.SHARK, AnimalEnum.SHARK, AnimalEnum.SHARK);
                output.put("Cat", 0);
                output.put("Shark", 2);
                output.put("Dog", 0);
                output.put("Wolf", 0);
                break;
            case 2:
                // случай, когда дубликатов нет
                input.put("Cat", List.of(cat1, cat3, cat2));
                input.put("Dog", List.of(dog1));
                input.put("Wolf", List.of(wolf1, wolf2));
                input.put("Shark", List.of(shark1));
                types = List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF,
                        AnimalEnum.WOLF, AnimalEnum.SHARK);
                output.put("Cat", 0);
                output.put("Shark", 0);
                output.put("Dog", 0);
                output.put("Wolf", 0);
                break;
            case 3:
                // случай, когда в массиве животных есть null-значения
                input.put("Cat", List.of(cat1, cat3));
                input.put("Dog", List.of(dog1));
                input.put("Wolf", Arrays.asList(null, null));
                types = Arrays.asList(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, null);
                output.put("Cat", 0);
                output.put("Shark", 0);
                output.put("Dog", 0);
                output.put("Wolf", 0);
                break;
            case 4:
                // случай, когда список животных равен null
                input.put("Cat", List.of(cat1, cat3));
                input.put("Dog", null);
                types = Arrays.asList(AnimalEnum.CAT, AnimalEnum.CAT);
                output.put("Cat", 0);
                output.put("Shark", 0);
                output.put("Dog", 0);
                output.put("Wolf", 0);
                break;
            case 5:
                // случай, когда key равен null
                input.put("Cat", List.of(cat1, cat3));
                input.put(null, List.of(dog1));
                types = Arrays.asList(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG);
                output.put("Cat", 0);
                output.put("Shark", 0);
                output.put("Dog", 0);
                output.put("Wolf", 0);
                break;
            case 6:
                // вариант, когда мапа равна null
                input = null;
                types = new ArrayList<>();
                break;
            case 7:
                // вариант, когда мапа пустая
                types = new ArrayList<>();
                output.put("Cat", 0);
                output.put("Shark", 0);
                output.put("Dog", 0);
                output.put("Wolf", 0);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }

        // задание поведения для MockBean
        when(createAnimalService.receiveCreatedAnimals()).thenReturn(input);
        when(createAnimalService.receiveAnimalType()).thenReturn(types);

        animalsRepository.fillStorage();
        if (value == 3 || value == 4 || value == 5) {
            assertThrows(IllegalStateException.class, () -> {
                animalsRepository.findDuplicate();
            });
        }
        else if (value == 6) {
            assertThrows(NullPointerException.class, () -> {
                animalsRepository.findDuplicate();
            });
        }
        else {
            assertEquals(output, animalsRepository.findDuplicate());
        }
    }
}
