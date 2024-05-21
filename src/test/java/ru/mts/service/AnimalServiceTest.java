package ru.mts.service;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import ru.mts.dto.AnimalDTO;
import ru.mts.exceptions.IllegalCollectionSizeException;
import ru.mts.exceptions.NegativeArgumentException;
import ru.mts.mapper.dto.AnimalDTOToAnimalMapper;
import ru.mts.mapper.dto.AnimalToAnimalDTOMapper;
import ru.mts.model.Animal;
import ru.mts.model.AnimalType;
import ru.mts.model.Breed;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class for testing application")
@SpringBootTest
@ActiveProfiles("test")
public class AnimalServiceTest {
    Animal cat1, cat2, cat3, cat4, dog1, dog2, wolf1, wolf2, shark1, shark2, sameCat2, sameShark1, sameCat3;
    AnimalType catType, dogType, wolfType, sharkType;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private BreedService breedService;
    @Autowired
    private AnimalTypeService animalTypeService;
    @Autowired
    private Flyway flyway;
    @Autowired
    private AnimalToAnimalDTOMapper animalDTOMapper;

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
        flyway.migrate();
        List<Breed> breeds = breedService.getBreeds();
        List<AnimalType> animalTypes = animalTypeService.getAnimalTypes();

        catType = animalTypes.get(0);
        dogType = animalTypes.get(1);
        wolfType = animalTypes.get(2);
        sharkType = animalTypes.get(3);

        cat1 = new Animal("Misa", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.now().minusYears(10).minusDays(1), catType, (short) Period.between(LocalDate.now().minusYears(10).minusDays(1), LocalDate.now()).getYears(), breeds.get(0), "hSE{FD0T_a97");
        cat2 = new Animal("Lelik", BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18), catType, (short) Period.between(LocalDate.of(2013, 4, 18), LocalDate.now()).getYears(), breeds.get(1), "J4x№PS}mp6cu");
        cat3 = new Animal("Richard", BigDecimal.valueOf(7000.2).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2008, 9, 9), catType, (short) Period.between(LocalDate.of(2008, 9, 9), LocalDate.now()).getYears(), breeds.get(2), "P?mlx7MHz+#G");
        cat4 = new Animal("Richard", BigDecimal.valueOf(13760.23).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.of(2004, 2, 14), catType, (short) Period.between(LocalDate.of(2004, 2, 14), LocalDate.now()).getYears(), breeds.get(2), "15$K?eKpl,nK");

        dog1 = new Animal("Bobik", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.now().minusYears(10), dogType, (short) Period.between(LocalDate.now().minusYears(10), LocalDate.now()).getYears(), breeds.get(4), "DfEK[a!XM#2,");
        dog2 = new Animal("Druzhok", BigDecimal.valueOf(32000.33).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2020, 2, 15), dogType, (short) Period.between(LocalDate.of(2020, 2, 15), LocalDate.now()).getYears(), breeds.get(5), "O:n~(I22.;!K");

        wolf1 = new Animal("Klik", BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.now().minusYears(10).plusDays(1), wolfType, (short) Period.between(LocalDate.now().minusYears(10).plusDays(1), LocalDate.now()).getYears(), breeds.get(11), "WjWP-R!59VM&");
        wolf2 = new Animal("Volchok", BigDecimal.valueOf(700000.157).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.of(1997, 2, 1), wolfType, (short) Period.between(LocalDate.of(1997, 2, 1), LocalDate.now()).getYears(), breeds.get(9), "n:C^=3vu_8zk");

        shark1 = new Animal("Sharki", BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1996, 6, 13), sharkType, (short) Period.between(LocalDate.of(1996, 6, 13), LocalDate.now()).getYears(), breeds.get(8), "1#Qw8#3u#U$^");
        shark2 = new Animal("Volna", BigDecimal.valueOf(1500000.99).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2019, 1, 10), sharkType, (short) Period.between(LocalDate.of(2019, 1, 10), LocalDate.now()).getYears(), breeds.get(7), "BP{8EC1Jl-xV");

        sameCat2 = new Animal("Lelik", BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18), catType, (short) Period.between(LocalDate.of(2013, 4, 18), LocalDate.now()).getYears(), breeds.get(1), "J4x№PS}mp6cu");
        sameShark1 = new Animal("Sharki", BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1996, 6, 13), sharkType, (short) Period.between(LocalDate.of(1996, 6, 13), LocalDate.now()).getYears(), breeds.get(8), "1#Qw8#3u#U$^");
        sameCat3 = new Animal("Richard", BigDecimal.valueOf(7000.2).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2008, 9, 9), catType, (short) Period.between(LocalDate.of(2008, 9, 9), LocalDate.now()).getYears(), breeds.get(2), "P?mlx7MHz+#G");

    }

    @DisplayName("Test findLeapYearNames method")
    @ParameterizedTest(name = "Test {arguments}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6})
    public void findLeapYearNames(int value) {
        List<Animal> animals = new ArrayList<>();
        Map<String, LocalDate> leapYearAnimals = new HashMap<>();

        initAnimals();
        switch (value) {
            case 0 -> {
                animals = List.of(cat1, cat3, cat2, dog1, wolf1, wolf2, shark1);

                leapYearAnimals.put("cat " + cat3.getName(), LocalDate.of(2008, 9, 9));
                leapYearAnimals.put("shark " + shark1.getName(), LocalDate.of(1996, 6, 13));
            }
            case 1 -> animals = List.of(cat1, cat2, dog1, wolf1, wolf2);
            case 2 -> animals = Arrays.asList(shark1, wolf1, null);
            case 3 -> animals = List.of();
            case 4 -> animals = null;
            case 5 -> {}
            case 6 -> {
                animals = List.of(cat3, cat1, cat4, shark1);
                leapYearAnimals.put("cat " + cat3.getName(), LocalDate.of(2008, 9, 9));
                leapYearAnimals.put("shark " + shark1.getName(), LocalDate.of(1996, 6, 13));
            }
            default -> throw new IllegalStateException("Unexpected value: " + value);
        }

        List<Animal> finalAnimals = animals;
        if (value == 2 || value == 4) {
            assertThrows(NullPointerException.class, () -> {
                animalService.saveAnimals(finalAnimals.stream().map(animalDTOMapper).collect(Collectors.toList()));
            });
        }
        else {
            animalService.saveAnimals(finalAnimals.stream().map(animalDTOMapper).collect(Collectors.toList()));
        }

        animalService.fillStorage();
        assertEquals(leapYearAnimals, animalService.findLeapYearNames());

        animalService.deleteAnimals();
    }

    @DisplayName("Test findOlderAnimal method")
    @ParameterizedTest(name = "Array of animals, more than {arguments} y.o.")
    @ValueSource(ints = {10, 9, 20, 50, 24, 12, 18, 0, -2})
    public void findOlderAnimal(int value) {
        List<Animal> animals;

        initAnimals();
        animals = List.of(cat1, cat3, cat2, dog1, wolf1, wolf2, shark1);

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
                assertThrows(NullPointerException.class, () -> {
                    animalService.saveAnimals(Stream.of(cat1, null, dog1).map(animalDTOMapper).collect(Collectors.toList()));
                });
                break;
            case 12:
                animalService.saveAnimals(List.of());
                break;
            case 18:
                assertThrows(NullPointerException.class, () -> {
                    animalService.saveAnimals(null);
                });
                break;
            case 0:
                animalService.saveAnimals(Stream.of(cat1, cat2, dog1).map(animalDTOMapper).collect(Collectors.toList()));
                break;
            default:
                    animalService.saveAnimals(animals.stream().map(animalDTOMapper).collect(Collectors.toList()));
                break;
        }

        animalService.fillStorage();
        if (value == -2) {
            assertThrows(NegativeArgumentException.class, () -> {
                animalService.findOlderAnimal(value);
            });
        } else {
            assertTrue(containsHashMap(outputResults, animalService.findOlderAnimal(value)));
        }

        animalService.deleteAnimals();
    }

    @DisplayName("Test findDuplicate method")
    @ParameterizedTest(name = "Test {arguments}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6})
    public void findDuplicate(int value) {
        List<Animal> animals = new ArrayList<>();
        Map<String, List<Animal>> duplicates = new HashMap<>();

        initAnimals();
        switch (value) {
            case 0:
                animals = List.of(cat1, cat3, sameCat3, cat3, cat2, sameCat2, dog1, wolf1, wolf2, shark1, sameShark1, shark1, sameShark1);

                duplicates.put("cat", List.of(cat3, sameCat3, cat3, cat2, sameCat2));
                duplicates.put("shark", List.of(shark1, sameShark1, shark1, sameShark1));
                break;
            case 1:
                animals = List.of(cat1, cat3, cat2, dog1, wolf1, wolf2, shark1, shark1, shark1);

                duplicates.put("shark", List.of(shark1, shark1, shark1));
                break;
            case 2:
                animals = List.of(cat1, cat3, cat2, dog1, wolf1, wolf2, shark1);
                break;
            case 3:
                animals = Arrays.asList(cat1, cat3, dog1, null, null);
                break;
            case 4:
                animals = null;
                break;
            case 5:
                break;
            case 6:
                animals = List.of(cat1, cat2, cat3, sameCat2, cat2, cat1, dog1, wolf1, wolf2, shark1, shark1);

                duplicates.put("cat", List.of(cat1, cat2, sameCat2, cat2, cat1));
                duplicates.put("shark", List.of(shark1, shark1));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }

        List<Animal> finalAnimals = animals;
        if (value == 3 || value == 4) {
            assertThrows(NullPointerException.class, () -> {
                animalService.saveAnimals(finalAnimals.stream().map(animalDTOMapper).collect(Collectors.toList()));
            });
        }
        else {
            animalService.saveAnimals(finalAnimals.stream().map(animalDTOMapper).collect(Collectors.toList()));
        }


        animalService.fillStorage();

        assertEquals(duplicates, animalService.findDuplicate());

        animalService.deleteAnimals();
    }

    @DisplayName("Test findAverageAge method")
    @ParameterizedTest(name = "Test {arguments}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void findAverageAge(int value) {
        List<Animal> animals = new ArrayList<>();
        double averageAge = 0;

        initAnimals();
        switch (value) {
            case 0:
                animals = List.of(cat1, cat3, cat2, dog1, wolf1, wolf2, shark1);
                averageAge = 15.57;
                break;
            case 1:
                animals = List.of(cat1, cat2, dog1, wolf1, wolf2);
                averageAge = 13.4;
                break;
            case 2:
                animals = Arrays.asList(shark1, wolf1, null);
                break;
            case 3:
                animals = List.of();
                break;
            case 4:
                animals = null;
                break;
            case 5:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }

        List<Animal> finalAnimals = animals;
        if (value == 2 || value == 4) {
            assertThrows(NullPointerException.class, () -> {
                animalService.saveAnimals(finalAnimals.stream().map(animalDTOMapper).collect(Collectors.toList()));
            });
        }
        else {
            animalService.saveAnimals(finalAnimals.stream().map(animalDTOMapper).collect(Collectors.toList()));
        }

        animalService.fillStorage();
        assertEquals(averageAge, animalService.findAverageAge());

        animalService.deleteAnimals();
    }

    @DisplayName("Test findOldAndExpensive method")
    @ParameterizedTest(name = "Test {arguments}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void findOldAndExpensive(int value) {
        List<Animal> animals = new ArrayList<>();
        List<Animal> oldAndExpensiveAnimals = new ArrayList<>();

        initAnimals();
        switch (value) {
            case 0:
                animals = List.of(cat1, cat3, dog1, dog2, wolf1, shark1, shark2);
                oldAndExpensiveAnimals = List.of(shark1, wolf1);
                break;
            case 1:
                animals = List.of(cat1, cat2, dog1, shark2);
                break;
            case 2:
                animals = Arrays.asList(shark1, wolf1, null);
                break;
            case 3:
                animals = List.of();
                break;
            case 4:
                animals = null;
                break;
            case 5:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }

        List<Animal> finalAnimals = animals;
        if (value == 2 || value == 4) {
            assertThrows(NullPointerException.class, () -> {
                animalService.saveAnimals(finalAnimals.stream().map(animalDTOMapper).collect(Collectors.toList()));
            });
        }
        else {
            animalService.saveAnimals(finalAnimals.stream().map(animalDTOMapper).collect(Collectors.toList()));
        }

        animalService.fillStorage();
        assertEquals(oldAndExpensiveAnimals, animalService.findOldAndExpensive());

        animalService.deleteAnimals();
    }

    @DisplayName("Test findMinCostAnimals method")
    @ParameterizedTest(name = "Test {arguments}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void findMinCostAnimals(int value) throws IllegalCollectionSizeException {
        List<Animal> animals = new ArrayList<>();
        List<String> minCostAnimals = new ArrayList<>();

        initAnimals();
        switch (value) {
            case 0:
                animals = List.of(cat3, dog1, dog2, wolf1, shark1, shark2);

                // cat3, dog1, dog2
                // Richard, Bobik, Druzhok
                // Richard, Druzhok, Bobik
                minCostAnimals = List.of(cat3.getName(), dog2.getName(), dog1.getName());
                break;
            case 1:
                animals = List.of(cat1, cat2, dog1, shark2);

                // cat1, cat2, dog1
                // Misa, Lelik, Bobik
                minCostAnimals = List.of(cat1.getName(), cat2.getName(), dog1.getName());
                break;
            case 2:
                animals = Arrays.asList(shark1, wolf1, null);
                break;
            case 3:
                animals = List.of();
                break;
            case 4:
                animals = null;
                break;
            case 5:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }

        List<Animal> finalAnimals = animals;
        if (value == 2 || value == 4) {
            assertThrows(NullPointerException.class, () -> {
                animalService.saveAnimals(finalAnimals.stream().map(animalDTOMapper).collect(Collectors.toList()));
            });
        }
        else {
            animalService.saveAnimals(finalAnimals.stream().map(animalDTOMapper).collect(Collectors.toList()));
        }

        animalService.fillStorage();
        if (value == 2 || value == 3 || value == 5) {
            assertThrows(IllegalCollectionSizeException.class, () -> {
                animalService.findMinCostAnimals();
            });
        } else if (value == 4) {
            System.out.println();
        } else {
            assertEquals(minCostAnimals, animalService.findMinCostAnimals());
        }

        animalService.deleteAnimals();
    }
}
