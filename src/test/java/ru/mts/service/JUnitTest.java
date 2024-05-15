package ru.mts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mts.exceptions.IllegalCollectionSizeException;
import ru.mts.exceptions.NegativeArgumentException;
import ru.mts.model.Animal;
import ru.mts.model.AnimalType;
import ru.mts.model.Breed;
import ru.mts.repository.AnimalRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Class for unit testing")
class JUnitTest {
    @Nested
    @DisplayName("Class for testing method equals from AbstractAnimal")
    public class AbstractAnimalTest {
        AnimalType catType, dogType, wolfType, sharkType;
        List<String> breeds = List.of("Британская", "Шотландская", "Сфинкс", "Немецкая овчарка", "Доберман", "Лабрадор", "Тигровая", "Белая", "Молот", "Полярный", "Ньюфаундлендский", "Японский");

        @Test
        @DisplayName("Test override method equals")
        public void equals() {
            catType = new AnimalType("cat", false);
            dogType = new AnimalType("dog", false);
            wolfType = new AnimalType("wolf", true);
            sharkType = new AnimalType("shark", true);

            Breed breed1 = new Breed(breeds.get(0), catType);
            Breed breed2 = new Breed(breeds.get(1), catType);
            Breed breed3 = new Breed(breeds.get(2), catType);
            catType.setBreeds(new ArrayList<>(List.of(breed1, breed2, breed3)));

            Breed breed4 = new Breed(breeds.get(3), dogType);
            Breed breed5 = new Breed(breeds.get(4), dogType);
            Breed breed6 = new Breed(breeds.get(5), dogType);
            dogType.setBreeds(new ArrayList<>(List.of(breed4, breed5, breed6)));

            Breed breed7 = new Breed(breeds.get(6), sharkType);
            Breed breed8 = new Breed(breeds.get(7), sharkType);
            Breed breed9 = new Breed(breeds.get(8), sharkType);
            sharkType.setBreeds(new ArrayList<>(List.of(breed7, breed8, breed9)));

            Breed breed10 = new Breed(breeds.get(9), wolfType);
            Breed breed11 = new Breed(breeds.get(10), wolfType);
            Breed breed12 = new Breed(breeds.get(11), wolfType);
            sharkType.setBreeds(new ArrayList<>(List.of(breed10, breed11, breed12)));

            // создание животных
            Animal cat1 = new Animal("Миса", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.now().minusYears(10).minusDays(1), catType, (short) Period.between(LocalDate.now().minusYears(10).minusDays(1), LocalDate.now()).getYears(), breed1, "hSE{FD0T_a97");
            Animal dog = new Animal("Бобик", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.now().minusYears(10), dogType, (short) Period.between(LocalDate.now().minusYears(10), LocalDate.now()).getYears(), breed5, "DfEK[a!XM#2,");
            Animal cat2 = new Animal("Лёлик", BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18), catType, (short) Period.between(LocalDate.of(2013, 4, 18), LocalDate.now()).getYears(), breed2, "J4x№PS}mp6cu");
            Animal anotherDog = new Animal("Дружок", BigDecimal.valueOf(32000.33).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2020, 2, 15), dogType, (short) Period.between(LocalDate.of(2020, 2, 15), LocalDate.now()).getYears(), breed6, "O:n~(I22.;!K");
            Animal sameCat1 = new Animal("Миса", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.now().minusYears(10).minusDays(1), catType, (short) Period.between(LocalDate.now().minusYears(10).minusDays(1), LocalDate.now()).getYears(), breed1, "hSE{FD0T_a97");
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
    public class AnimalRepositoryImplTest {
        @Mock
        ObjectMapper objectMapper;
        @Mock
        AnimalRepository animalRepository;
        Animal cat1, cat2, cat3, cat4, dog1, dog2, wolf1, wolf2, shark1, shark2, sameCat2, sameShark1, sameCat3;
        AnimalType catType, dogType, wolfType, sharkType;
        List<String> breeds = List.of("Британская", "Шотландская", "Сфинкс", "Немецкая овчарка", "Доберман", "Лабрадор", "Тигровая", "Белая", "Молот", "Полярный", "Ньюфаундлендский", "Японский");


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
        public static boolean containsHashMap(List<HashMap<Animal, Short>> list, Map<Animal, Short> targetMap) {
            for (HashMap<Animal, Short> map : list) {
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
            catType = new AnimalType("cat", false);
            dogType = new AnimalType("dog", false);
            wolfType = new AnimalType("wolf", true);
            sharkType = new AnimalType("shark", true);

            Breed breed1 = new Breed(breeds.get(0), catType);
            Breed breed2 = new Breed(breeds.get(1), catType);
            Breed breed3 = new Breed(breeds.get(2), catType);
            catType.setBreeds(new ArrayList<>(List.of(breed1, breed2, breed3)));

            Breed breed4 = new Breed(breeds.get(3), dogType);
            Breed breed5 = new Breed(breeds.get(4), dogType);
            Breed breed6 = new Breed(breeds.get(5), dogType);
            dogType.setBreeds(new ArrayList<>(List.of(breed4, breed5, breed6)));

            Breed breed7 = new Breed(breeds.get(6), sharkType);
            Breed breed8 = new Breed(breeds.get(7), sharkType);
            Breed breed9 = new Breed(breeds.get(8), sharkType);
            sharkType.setBreeds(new ArrayList<>(List.of(breed7, breed8, breed9)));

            Breed breed10 = new Breed(breeds.get(9), wolfType);
            Breed breed11 = new Breed(breeds.get(10), wolfType);
            Breed breed12 = new Breed(breeds.get(11), wolfType);
            sharkType.setBreeds(new ArrayList<>(List.of(breed10, breed11, breed12)));

            cat1 = new Animal("Misa", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.now().minusYears(10).minusDays(1), catType, (short) Period.between(LocalDate.now().minusYears(10).minusDays(1), LocalDate.now()).getYears(), breed1, "hSE{FD0T_a97");
            cat2 = new Animal("Lelik", BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18), catType, (short) Period.between(LocalDate.of(2013, 4, 18), LocalDate.now()).getYears(), breed2, "J4x№PS}mp6cu");
            cat3 = new Animal("Richard", BigDecimal.valueOf(7000.2).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2008, 9, 9), catType, (short) Period.between(LocalDate.of(2008, 9, 9), LocalDate.now()).getYears(), breed3, "P?mlx7MHz+#G");
            cat4 = new Animal("Richard", BigDecimal.valueOf(13760.23).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.of(2004, 2, 14), catType, (short) Period.between(LocalDate.of(2004, 2, 14), LocalDate.now()).getYears(), breed3, "15$K?eKpl,nK");

            dog1 = new Animal("Bobik", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.now().minusYears(10), dogType, (short) Period.between(LocalDate.now().minusYears(10), LocalDate.now()).getYears(), breed5, "DfEK[a!XM#2,");
            dog2 = new Animal("Druzhok", BigDecimal.valueOf(32000.33).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2020, 2, 15), dogType, (short) Period.between(LocalDate.of(2020, 2, 15), LocalDate.now()).getYears(), breed6, "O:n~(I22.;!K");

            wolf1 = new Animal("Klik", BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.now().minusYears(10).plusDays(1), wolfType, (short) Period.between(LocalDate.now().minusYears(10).plusDays(1), LocalDate.now()).getYears(), breed12, "WjWP-R!59VM&");
            wolf2 = new Animal("Volchok", BigDecimal.valueOf(700000.157).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.of(1997, 2, 1), wolfType, (short) Period.between(LocalDate.of(1997, 2, 1), LocalDate.now()).getYears(), breed10, "n:C^=3vu_8zk");

            shark1 = new Animal("Sharki", BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1996, 6, 13), sharkType, (short) Period.between(LocalDate.of(1996, 6, 13), LocalDate.now()).getYears(), breed9, "1#Qw8#3u#U$^");
            shark2 = new Animal("Volna", BigDecimal.valueOf(1500000.99).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2019, 1, 10), sharkType, (short) Period.between(LocalDate.of(2019, 1, 10), LocalDate.now()).getYears(), breed8, "BP{8EC1Jl-xV");

            sameCat2 = new Animal("Lelik", BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18), catType, (short) Period.between(LocalDate.of(2013, 4, 18), LocalDate.now()).getYears(), breed2, "J4x№PS}mp6cu");
            sameShark1 = new Animal("Sharki", BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1996, 6, 13), sharkType, (short) Period.between(LocalDate.of(1996, 6, 13), LocalDate.now()).getYears(), breed9, "1#Qw8#3u#U$^");
            sameCat3 = new Animal("Richard", BigDecimal.valueOf(7000.2).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2008, 9, 9), catType, (short) Period.between(LocalDate.of(2008, 9, 9), LocalDate.now()).getYears(), breed3, "P?mlx7MHz+#G");
        }

        @DisplayName("Test method findLeapYearNames")
        @ParameterizedTest(name = "Test {arguments}")
        @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6})
        public void findLeapYearNames(int value) {
            AnimalService animalService = new AnimalService(animalRepository, objectMapper);

            ConcurrentHashMap<String, List<Animal>> animals = new ConcurrentHashMap<>();
            Map<String, LocalDate> leapYearAnimals = new HashMap<>();

            initAnimals();
            switch (value) {
                case 0:
                    animals.put("cat", List.of(cat1, cat3, cat2));
                    animals.put("dog", List.of(dog1));
                    animals.put("wolf", List.of(wolf1, wolf2));
                    animals.put("shark", List.of(shark1));

                    leapYearAnimals.put("cat " + cat3.getName(), LocalDate.of(2008, 9, 9));
                    leapYearAnimals.put("shark " + shark1.getName(), LocalDate.of(1996, 6, 13));
                    break;
                case 1:
                    animals.put("cat", List.of(cat1, cat2));
                    animals.put("dog", List.of(dog1));
                    animals.put("wolf", List.of(wolf1, wolf2));
                    break;
                case 2:
                    animals.put("shark", List.of(shark1));
                    animals.put("dog", new ArrayList<>());
                    animals.put("wolf", Arrays.asList(wolf1, null));
                    animals.put("cat", new ArrayList<>());

                    leapYearAnimals.put("shark " + shark1.getName(), shark1.getBirthDate());
                    break;
                case 3:
                    animals.put("cat", new ArrayList<>());
                    animals.put("dog", new ArrayList<>());
                    animals.put("wolf", new ArrayList<>());
                    animals.put("shark", new ArrayList<>());
                    break;
                case 4:
                    animals = null;
                    break;
                case 5:
                    break;
                case 6:
                    animals.put("cat", List.of(cat3, cat1, cat4));
                    animals.put("shark", List.of(shark1));

                    leapYearAnimals.put("cat " + cat3.getName(), LocalDate.of(2008, 9, 9));
                    leapYearAnimals.put("shark " + shark1.getName(), LocalDate.of(1996, 6, 13));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            }

            animalService.setAnimalStorage(animals);
            if (value == 2 || value == 4) {
                assertThrows(NullPointerException.class, () -> animalService.findLeapYearNames());
            } else {
                assertEquals(leapYearAnimals, animalService.findLeapYearNames());
            }
        }

        @DisplayName("Test method findOlderAnimal")
        @ParameterizedTest(name = "Array of animals, more than {arguments} y.o.")
        @ValueSource(ints = {10, 9, 20, 50, 24, 12, 18, 0, -2})
        public void findOlderAnimal(int value) {
            AnimalService animalService = new AnimalService(animalRepository, objectMapper);

            ConcurrentHashMap<String, List<Animal>> animals = new ConcurrentHashMap<>();

            initAnimals();

            List<HashMap<Animal, Short>> outputResults = List.of(
                    new HashMap<>() {{
                        put(cat3, (short) 15);
                        put(cat2, (short) 11);
                        put(wolf2, (short) 27);
                        put(shark1, (short) 27);
                    }},
                    new HashMap<>() {{
                        put(cat1, (short) 10);
                        put(cat2, (short) 11);
                        put(cat3, (short) 15);
                        put(dog1, (short) 10);
                        put(wolf2, (short) 27);
                        put(shark1, (short) 27);
                    }},
                    new HashMap<>() {{
                        put(wolf2, (short) 27);
                        put(shark1, (short) 27);
                    }},
                    new HashMap<>() {{
                        put(shark1, (short) 27);
                    }},
                    new HashMap<>() {{
                        put(null, (short) 0);
                    }},

                    new HashMap<>() {{
                        put(dog1, (short) 10);
                    }},

                    new HashMap<>() {{
                        put(cat1, (short) 10);
                        put(cat2, (short) 11);
                        put(dog1, (short) 10);
                    }},

                    new HashMap<>(),

                    new HashMap<>() {{
                        put(cat1, (short) 10);
                    }}
            );

            switch (value) {
                case 24:
                    animals.put("cat", Arrays.asList(cat1, null));
                    animals.put("dog", List.of(dog1));
                    break;
                case 12:
                    animals.put("cat", new ArrayList<>());
                    animals.put("dog", new ArrayList<>());
                    break;
                case 18:
                    animals = null;
                    break;
                case 0:
                    animals.put("cat", new ArrayList<>(List.of(cat1, cat2)));
                    animals.put("dog", new ArrayList<>(List.of(dog1)));
                    break;
                default:
                    animals.put("cat", List.of(cat1, cat3, cat2));
                    animals.put("dog", List.of(dog1));
                    animals.put("wolf", List.of(wolf1, wolf2));
                    animals.put("shark", List.of(shark1));
                    break;
            }

            animalService.setAnimalStorage(animals);
            if (value == 24 || value == 18) {
                assertThrows(NullPointerException.class, () -> {
                    animalService.findOlderAnimal(value);
                });
            } else if (value == -2) {
                assertThrows(NegativeArgumentException.class, () -> {
                    animalService.findOlderAnimal(value);
                });
            } else {
                assertTrue(containsHashMap(outputResults, animalService.findOlderAnimal(value)));
            }
        }

        @DisplayName("Test method findDuplicate")
        @ParameterizedTest(name = "Test {arguments}")
        @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6})
        public void findDuplicate(int value) {
            AnimalService animalService = new AnimalService(animalRepository, objectMapper);

            ConcurrentHashMap<String, List<Animal>> animals = new ConcurrentHashMap<>();
            Map<String, List<Animal>> duplicates = new HashMap<>();

            initAnimals();
            switch (value) {
                case 0:
                    animals.put("cat", List.of(cat1, cat3, sameCat3, cat3, cat2, sameCat2));
                    animals.put("dog", List.of(dog1));
                    animals.put("wolf", List.of(wolf1, wolf2));
                    animals.put("shark", List.of(shark1, sameShark1, shark1, sameShark1));

                    duplicates.put("cat", List.of(cat3, sameCat3, cat3, cat2, sameCat2));
                    duplicates.put("shark", List.of(shark1, sameShark1, shark1, sameShark1));
                    break;
                case 1:
                    animals.put("cat", List.of(cat1, cat3, cat2));
                    animals.put("dog", List.of(dog1));
                    animals.put("wolf", List.of(wolf1, wolf2));
                    animals.put("shark", List.of(shark1, shark1, shark1));

                    duplicates.put("shark", List.of(shark1, shark1, shark1));
                    break;
                case 2:
                    animals.put("cat", List.of(cat1, cat3, cat2));
                    animals.put("dog", List.of(dog1));
                    animals.put("wolf", List.of(wolf1, wolf2));
                    animals.put("shark", List.of(shark1));
                    break;
                case 3:
                    animals.put("cat", List.of(cat1, cat3));
                    animals.put("dog", List.of(dog1));
                    animals.put("wolf", Arrays.asList(null, null));
                    break;
                case 4:
                    animals = null;
                    break;
                case 5:
                    break;
                case 6:
                    animals.put("cat", List.of(cat1, cat2, cat3, sameCat2, cat2, cat1));
                    animals.put("dog", List.of(dog1));
                    animals.put("wolf", List.of(wolf1, wolf2));
                    animals.put("shark", List.of(shark1, shark1));

                    duplicates.put("cat", List.of(cat1, cat2, sameCat2, cat2, cat1));
                    duplicates.put("shark", List.of(shark1, shark1));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            }

            animalService.setAnimalStorage(animals);
            if (value == 3 || value == 4) {
                assertThrows(NullPointerException.class, () -> {
                    animalService.findDuplicate();
                });
            } else {
                assertEquals(duplicates, animalService.findDuplicate());
            }
        }

        @DisplayName("Test findAverageAge method")
        @ParameterizedTest(name = "Test {arguments}")
        @ValueSource(ints = {0, 1, 2, 3, 4, 5})
        public void findAverageAge(int value) {
            AnimalService animalService = new AnimalService(animalRepository, objectMapper);

            ConcurrentHashMap<String, List<Animal>> animals = new ConcurrentHashMap<>();
            double averageAge = 0;

            initAnimals();
            switch (value) {
                case 0:
                    animals.put("cat", List.of(cat1, cat3, cat2));
                    animals.put("dog", List.of(dog1));
                    animals.put("wolf", List.of(wolf1, wolf2));
                    animals.put("shark", List.of(shark1));

                    averageAge = 15.57;
                    break;
                case 1:
                    animals.put("cat", List.of(cat1, cat2));
                    animals.put("dog", List.of(dog1));
                    animals.put("wolf", List.of(wolf1, wolf2));

                    averageAge = 13.4;
                    break;
                case 2:
                    animals.put("shark", List.of(shark1));
                    animals.put("dog", new ArrayList<>());
                    animals.put("wolf", Arrays.asList(wolf1, null));
                    animals.put("cat", new ArrayList<>());
                    break;
                case 3:
                    animals.put("cat", new ArrayList<>());
                    animals.put("dog", new ArrayList<>());
                    animals.put("wolf", new ArrayList<>());
                    animals.put("shark", new ArrayList<>());

                    break;
                case 4:
                    animals = null;
                    break;
                case 5:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            }

            animalService.setAnimalStorage(animals);
            if (value == 2 || value == 4) {
                assertThrows(NullPointerException.class, () -> {
                    animalService.findAverageAge();
                });
            } else {
                assertEquals(averageAge, animalService.findAverageAge());
            }
        }

        @DisplayName("Test findOldAndExpensive method")
        @ParameterizedTest(name = "Test {arguments}")
        @ValueSource(ints = {0, 1, 2, 3, 4, 5})
        public void findOldAndExpensive(int value) {
            AnimalService animalService = new AnimalService(animalRepository, objectMapper);

            ConcurrentHashMap<String, List<Animal>> animals = new ConcurrentHashMap<>();
            List<Animal> oldAndExpensiveAnimals = new ArrayList<>();

            initAnimals();
            switch (value) {
                case 0:
                    animals.put("cat", List.of(cat1, cat3));
                    animals.put("dog", List.of(dog1, dog2));
                    animals.put("wolf", List.of(wolf1));
                    animals.put("shark", List.of(shark1, shark2));

                    oldAndExpensiveAnimals = List.of(shark1, wolf1);
                    break;
                case 1:
                    animals.put("cat", List.of(cat1, cat2));
                    animals.put("dog", List.of(dog1));
                    animals.put("shark", List.of(shark2));
                    break;
                case 2:
                    animals.put("shark", List.of(shark1));
                    animals.put("dog", new ArrayList<>());
                    animals.put("wolf", Arrays.asList(wolf1, null));
                    animals.put("cat", new ArrayList<>());
                    break;
                case 3:
                    animals.put("cat", new ArrayList<>());
                    animals.put("dog", new ArrayList<>());
                    animals.put("wolf", new ArrayList<>());
                    animals.put("shark", new ArrayList<>());
                    break;
                case 4:
                    animals = null;
                    break;
                case 5:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            }

            animalService.setAnimalStorage(animals);
            if (value == 2 || value == 4) {
                assertThrows(NullPointerException.class, () -> {
                    animalService.findOldAndExpensive();
                });
            } else {
                assertEquals(oldAndExpensiveAnimals, animalService.findOldAndExpensive());
            }
        }

        @DisplayName("Test findMinCostAnimals method")
        @ParameterizedTest(name = "Test {arguments}")
        @ValueSource(ints = {0, 1, 2, 3, 4, 5})
        public void findMinCostAnimals(int value) throws IllegalCollectionSizeException {
            AnimalService animalService = new AnimalService(animalRepository, objectMapper);

            ConcurrentHashMap<String, List<Animal>> animals = new ConcurrentHashMap<>();
            List<String> minCostAnimals = new ArrayList<>();

            initAnimals();
            switch (value) {
                case 0:
                    animals.put("cat", List.of(cat3));
                    animals.put("dog", List.of(dog1, dog2));
                    animals.put("wolf", List.of(wolf1));
                    animals.put("shark", List.of(shark1, shark2));

                    // cat3, dog1, dog2
                    // Richard, Bobik, Druzhok
                    // Richard, Druzhok, Bobik
                    minCostAnimals = List.of(cat3.getName(), dog2.getName(), dog1.getName());
                    break;
                case 1:
                    animals.put("cat", List.of(cat1, cat2));
                    animals.put("dog", List.of(dog1));
                    animals.put("shark", List.of(shark2));

                    // cat1, cat2, dog1
                    // Misa, Lelik, Bobik
                    minCostAnimals = List.of(cat1.getName(), cat2.getName(), dog1.getName());
                    break;
                case 2:
                    animals.put("shark", List.of(shark1));
                    animals.put("dog", new ArrayList<>());
                    animals.put("wolf", Arrays.asList(wolf1, null));
                    animals.put("cat", new ArrayList<>());
                    break;
                case 3:
                    animals.put("cat", new ArrayList<>());
                    animals.put("dog", new ArrayList<>());
                    animals.put("wolf", new ArrayList<>());
                    animals.put("shark", new ArrayList<>());
                    break;
                case 4:
                    animals = null;
                    break;
                case 5:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            }

            animalService.setAnimalStorage(animals);
            if (value == 2 || value == 4) {
                assertThrows(NullPointerException.class, () -> {
                    animalService.findMinCostAnimals();
                });
            } else if (value == 3 || value == 5) {
                assertThrows(IllegalCollectionSizeException.class, () -> {
                    animalService.findMinCostAnimals();
                });
            } else {
                assertEquals(minCostAnimals, animalService.findMinCostAnimals());
            }
        }
    }
}
