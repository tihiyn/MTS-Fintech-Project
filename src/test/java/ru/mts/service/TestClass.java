package ru.mts.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mts.model.*;
import ru.mts.repository.AnimalsRepository;
import ru.mts.repository.AnimalsRepositoryImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@DisplayName("Outer class")
class TestClass {
    @Nested
    @DisplayName("Class for testing method equals from AbstractAnimal")
    public class FirstInnerClass {
        @Test
        @DisplayName("Test override method equals")
        public void equals() {
            // создание животных
            Animal cat1 = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2100, 7, 26));
            Animal dog = new Dog("Доберман", "Шарик", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.of(2000, 12, 1));
            Animal cat2 = new Cat("Сфинкс", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2400, 1, 5));
            Animal anotherDog = new Dog("Немецкая овчарка", "Жучка", BigDecimal.valueOf(15000).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 10, 12));
            Animal sameCat1 = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2100, 7, 26));
            Animal stillCat1 = cat1;

            assertNotEquals(cat1, dog);
            assertNotEquals(cat1, cat2);
            assertEquals(cat1, sameCat1);
            assertEquals(cat1, stillCat1);
            assertEquals(sameCat1, stillCat1);
            assertEquals(cat1, cat1);
            assertNotEquals(anotherDog, null);

            stillCat1 = null;
            assertEquals(stillCat1, stillCat1);
            assertEquals(stillCat1, null);
            assertNotEquals(stillCat1, sameCat1);
            sameCat1 = null;
            assertEquals(stillCat1, sameCat1);
        }
    }

    @Nested
    @DisplayName("Class for testing methods from AnimalsRepositoryImpl")
    @ExtendWith(MockitoExtension.class)
    public class SecondInnerClass {
        @Mock
        CreateAnimalService createAnimalService;
        Animal cat1, cat2, cat3, dog1, wolf1, wolf2, shark1, sameCat2, sameShark1, sameCat3;

        /**
         * Своя реализация метода contains.
         * Метод проверяет, содержится ли в списке из HashMap заданная HashMap
         *
         * @param list      список из HashMap
         * @param targetMap заданная HashMap
         * @return {@code true} если list содержит targetMap
         * @author Nikita
         * @since 1.5
         */
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
            cat1 = new Cat("Британская", "Миса", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.now().minusYears(10).minusDays(1));
            cat2 = new Cat("Шотландская", "Лёлик", BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18));
            cat3 = new Cat("Сфинкс", "Ричард", BigDecimal.valueOf(7000.2).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2008, 9, 9));

            dog1 = new Dog("Доберман", "Бобик", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.now().minusYears(10));

            wolf1 = new Wolf("Японский", "Клык", BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.now().minusYears(10).plusDays(1));
            wolf2 = new Wolf("Полярный", "Волчок", BigDecimal.valueOf(700000.157).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.of(1997, 2, 1));

            shark1 = new Shark("Молот", "Шарки", BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1996, 6, 13));

            sameCat2 = new Cat("Шотландская", "Лёлик", BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18));
            sameShark1 = new Shark("Молот", "Шарки", BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1996, 6, 13));
            sameCat3 = new Cat("Сфинкс", "Ричард", BigDecimal.valueOf(7000.2).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2008, 9, 9));
        }

        @DisplayName("Test method findLeapYearNames")
        @ParameterizedTest(name = "Test {arguments}")
        @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
        public void findLeapYearNames(int value) {
            AnimalsRepository animalsRepository = new AnimalsRepositoryImpl(createAnimalService);

            Map<AnimalEnum, List<Animal>> animals = new HashMap<>();
            List<AnimalEnum> animalTypes;
            Map<String, LocalDate> leapYearAnimals = new HashMap<>();

            initAnimals();
            switch (value) {
                case 0:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3, cat2));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));
                    animals.put(AnimalEnum.SHARK, List.of(shark1));

                    animalTypes = List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF, AnimalEnum.SHARK);

                    leapYearAnimals.put("CAT " + cat3.getName(), LocalDate.of(2008, 9, 9));
                    leapYearAnimals.put("SHARK " + shark1.getName(), LocalDate.of(1996, 6, 13));
                    break;
                case 1:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat2));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));

                    animalTypes = List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF);
                    break;
                case 2:
                    animals.put(AnimalEnum.SHARK, List.of(shark1));
                    animals.put(AnimalEnum.DOG, new ArrayList<>());
                    animals.put(AnimalEnum.WOLF, Arrays.asList(wolf1, null));
                    animals.put(AnimalEnum.CAT, new ArrayList<>());

                    animalTypes = Arrays.asList(AnimalEnum.SHARK, AnimalEnum.WOLF, null);
                    break;
                case 3:
                    animals.put(AnimalEnum.CAT, new ArrayList<>());
                    animals.put(AnimalEnum.DOG, new ArrayList<>());
                    animals.put(AnimalEnum.WOLF, new ArrayList<>());
                    animals.put(AnimalEnum.SHARK, new ArrayList<>());

                    animalTypes = new ArrayList<>();
                    break;
                case 4:
                    animals.put(AnimalEnum.CAT, null);
                    animals.put(AnimalEnum.DOG, List.of(dog1));

                    animalTypes = List.of(AnimalEnum.DOG);
                    break;
                case 5:
                    animals.put(null, new ArrayList<>());
                    animals.put(AnimalEnum.SHARK, List.of(shark1));

                    animalTypes = List.of(AnimalEnum.SHARK);
                    break;
                case 6:
                    animals = null;

                    animalTypes = new ArrayList<>();
                    break;
                case 7:
                    animalTypes = new ArrayList<>();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            }

            when(createAnimalService.receiveCreatedAnimals()).thenReturn(animals);
            when(createAnimalService.receiveAnimalTypes()).thenReturn(animalTypes);

            animalsRepository.fillStorage();
            if (value == 2 || value == 4 || value == 6) {
                assertThrows(NullPointerException.class, animalsRepository::findLeapYearNames);
            } else if (value == 5) {
                assertThrows(IllegalStateException.class, animalsRepository::findLeapYearNames);
            } else {
                assertEquals(leapYearAnimals, animalsRepository.findLeapYearNames());
            }
        }

        @DisplayName("Test method findOlderAnimal")
        @ParameterizedTest(name = "Array of animals, more than {arguments} y.o.")
        @ValueSource(ints = {10, 9, 20, 50, 24, 12, 18, 5})
        public void findOlderAnimal(int value) {
            AnimalsRepository animalsRepository = new AnimalsRepositoryImpl(createAnimalService);

            Map<AnimalEnum, List<Animal>> animals = new HashMap<>();

            initAnimals();
            animals.put(AnimalEnum.CAT, List.of(cat1, cat3, cat2));
            animals.put(AnimalEnum.DOG, List.of(dog1));
            animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));
            animals.put(AnimalEnum.SHARK, List.of(shark1));

            List<AnimalEnum> animalTypes = List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF, AnimalEnum.SHARK);
            List<HashMap<Animal, Integer>> listOfOlderThanValueAnimals = List.of(
                    new HashMap<>() {{
                        put(cat3, 15);
                        put(wolf2, 27);
                        put(shark1, 27);
                    }},
                    new HashMap<>() {{
                        put(cat1, 10);
                        put(cat2, 10);
                        put(cat3, 15);
                        put(dog1, 10);
                        put(wolf2, 27);
                        put(shark1, 27);
                    }},
                    new HashMap<>() {{
                        put(wolf2, 27);
                        put(shark1, 27);
                    }},
                    new HashMap<>() {{
                        put(shark1, 27);
                    }},
                    new HashMap<>() {{
                        put(null, 0);
                    }}
            );

            switch (value) {
                case 24:
                    when(createAnimalService.receiveCreatedAnimals()).thenReturn(new HashMap<>() {{
                        put(AnimalEnum.CAT, Arrays.asList(cat1, null));
                        put(AnimalEnum.DOG, List.of(dog1));
                    }});
                    when(createAnimalService.receiveAnimalTypes()).thenReturn(Arrays.asList(AnimalEnum.CAT, null, AnimalEnum.DOG));
                    break;
                case 12:
                    when(createAnimalService.receiveCreatedAnimals()).thenReturn(new HashMap<>() {{
                        put(AnimalEnum.CAT, new ArrayList<>());
                        put(AnimalEnum.DOG, new ArrayList<>());
                    }});
                    when(createAnimalService.receiveAnimalTypes()).thenReturn(new ArrayList<>());
                    break;
                case 18:
                    when(createAnimalService.receiveCreatedAnimals()).thenReturn(null);
                    when(createAnimalService.receiveAnimalTypes()).thenReturn(new ArrayList<>());
                    break;
                case 5:
                    when(createAnimalService.receiveCreatedAnimals()).thenReturn(new HashMap<>() {{
                        put(null, new ArrayList<>(List.of(cat1, cat2)));
                        put(AnimalEnum.DOG, new ArrayList<>(List.of(dog1)));
                    }});
                    when(createAnimalService.receiveAnimalTypes()).thenReturn(List.of(AnimalEnum.DOG));
                    break;
                default:
                    when(createAnimalService.receiveCreatedAnimals()).thenReturn(animals);
                    when(createAnimalService.receiveAnimalTypes()).thenReturn(animalTypes);
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
                assertTrue(containsHashMap(listOfOlderThanValueAnimals, animalsRepository.findOlderAnimal(value)));
            }
        }

        @DisplayName("Test method findDuplicate")
        @ParameterizedTest(name = "Test {arguments}")
        @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
        public void findDuplicate(int value) {
            AnimalsRepository animalsRepository = new AnimalsRepositoryImpl(createAnimalService);

            Map<AnimalEnum, List<Animal>> animals = new HashMap<>();
            List<AnimalEnum> animalTypes;
            Map<String, Integer> duplicates = new HashMap<>();

            initAnimals();
            switch (value) {
                case 0:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3, sameCat3, cat3, cat2, sameCat2, cat1));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));
                    animals.put(AnimalEnum.SHARK, List.of(shark1, sameShark1, shark1, sameShark1));
                    animalTypes = List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT,
                            AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF, AnimalEnum.WOLF,
                            AnimalEnum.SHARK, AnimalEnum.SHARK, AnimalEnum.SHARK, AnimalEnum.SHARK);
                    duplicates.put("CAT", 4);
                    duplicates.put("SHARK", 3);
                    duplicates.put("DOG", 0);
                    duplicates.put("WOLF", 0);
                    break;
                case 1:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3, cat2));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));
                    animals.put(AnimalEnum.SHARK, List.of(shark1, shark1, shark1));
                    animalTypes = List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF,
                            AnimalEnum.WOLF, AnimalEnum.SHARK, AnimalEnum.SHARK, AnimalEnum.SHARK);
                    duplicates.put("CAT", 0);
                    duplicates.put("SHARK", 2);
                    duplicates.put("DOG", 0);
                    duplicates.put("WOLF", 0);
                    break;
                case 2:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3, cat2));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, List.of(wolf1, wolf2));
                    animals.put(AnimalEnum.SHARK, List.of(shark1));
                    animalTypes = List.of(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF,
                            AnimalEnum.WOLF, AnimalEnum.SHARK);
                    duplicates.put("CAT", 0);
                    duplicates.put("SHARK", 0);
                    duplicates.put("DOG", 0);
                    duplicates.put("WOLF", 0);
                    break;
                case 3:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3));
                    animals.put(AnimalEnum.DOG, List.of(dog1));
                    animals.put(AnimalEnum.WOLF, Arrays.asList(null, null));
                    animalTypes = Arrays.asList(AnimalEnum.CAT, AnimalEnum.CAT, AnimalEnum.DOG, AnimalEnum.WOLF);
                    duplicates.put("CAT", 0);
                    duplicates.put("SHARK", 0);
                    duplicates.put("DOG", 0);
                    duplicates.put("WOLF", 0);
                    break;
                case 4:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3));
                    animals.put(AnimalEnum.DOG, null);
                    animalTypes = Arrays.asList(AnimalEnum.CAT, AnimalEnum.CAT);
                    duplicates.put("CAT", 0);
                    duplicates.put("SHARK", 0);
                    duplicates.put("DOG", 0);
                    duplicates.put("WOLF", 0);
                    break;
                case 5:
                    animals.put(AnimalEnum.CAT, List.of(cat1, cat3));
                    animals.put(null, List.of(dog1));
                    animalTypes = Arrays.asList(AnimalEnum.CAT, AnimalEnum.CAT);
                    duplicates.put("CAT", 0);
                    duplicates.put("SHARK", 0);
                    duplicates.put("DOG", 0);
                    duplicates.put("WOLF", 0);
                    break;
                case 6:
                    animals = null;
                    animalTypes = new ArrayList<>();
                    break;
                case 7:
                    animalTypes = new ArrayList<>();
                    duplicates.put("CAT", 0);
                    duplicates.put("SHARK", 0);
                    duplicates.put("DOG", 0);
                    duplicates.put("WOLF", 0);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            }

            when(createAnimalService.receiveCreatedAnimals()).thenReturn(animals);
            when(createAnimalService.receiveAnimalTypes()).thenReturn(animalTypes);

            animalsRepository.fillStorage();
            if (value == 4 || value == 5) {
                assertThrows(IllegalStateException.class, animalsRepository::findDuplicate);
            } else if (value == 6 || value == 3) {
                assertThrows(NullPointerException.class, animalsRepository::findDuplicate);
            } else {
                assertEquals(duplicates, animalsRepository.findDuplicate());
            }
        }
    }
}
