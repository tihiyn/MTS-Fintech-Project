package ru.mts.service;

import org.hibernate.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import ru.mts.AnimalsProperties;
import ru.mts.bpp.CreateAnimalServiceBeanPostProcessor;
import ru.mts.dao.AnimalDAO;
import ru.mts.dao.AnimalTypeDAO;
import ru.mts.dao.BreedDAO;
import ru.mts.model.Animal;
import ru.mts.model.AnimalType;
import ru.mts.model.Breed;
import ru.mts.util.ApplicationContextProvider;
import ru.mts.util.DBService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;

@DisplayName("Class for testing animals-configure-starter")
@SpringBootTest
@ActiveProfiles("test")
public class CreateAnimalServiceImplTest {
    Animal cat1, cat2, cat3, dog1, wolf1, wolf2, shark1;
    AnimalType catType, dogType, wolfType, sharkType;
    List<String> breeds = List.of("Британская", "Шотландская", "Сфинкс", "Немецкая овчарка", "Доберман", "Лабрадор", "Тигровая", "Белая", "Молот", "Полярный", "Ньюфаундлендский", "Японский");

    List<Animal> animals;
    @Autowired
    private CreateAnimalServiceBeanPostProcessor createAnimalServiceBeanPostProcessor;
    @Autowired
    private AnimalsProperties animalsProperties;
    @MockBean
    private CreateAnimalServiceImpl createAnimalService;
    @Autowired
    private AnimalDAO animalDAO;
    @Autowired
    private AnimalTypeDAO animalTypeDAO;
    @Autowired
    private BreedDAO breedDAO;

    /**
     * Метод для инициализации животных
     *
     * @author Nikita
     * @since 1.4
     */
    private void initAnimals() {
        Transaction transaction = DBService.getTransaction();

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

        List<AnimalType> animalTypes = List.of(catType, dogType, wolfType, sharkType);
        animalTypeDAO.saveListAnimalType(animalTypes);

        List<Breed> breeds = List.of(breed1, breed2, breed3, breed4, breed5, breed6, breed7, breed8, breed9, breed10, breed11, breed12);
        breedDAO.saveListOfBreeds(breeds);

        transaction.commit();

        cat1 = new Animal(animalsProperties.getCatNames().get(1), BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.now().minusYears(10).minusDays(1), catType, (short) Period.between(LocalDate.now().minusYears(10).minusDays(1), LocalDate.now()).getYears(), breed1, "hSE{FD0T_a97");
        cat2 = new Animal(animalsProperties.getCatNames().get(0), BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18), catType, (short) Period.between(LocalDate.of(2013, 4, 18), LocalDate.now()).getYears(), breed2, "J4x№PS}mp6cu");
        cat3 = new Animal(animalsProperties.getCatNames().get(2), BigDecimal.valueOf(7000.2).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2008, 9, 9), catType, (short) Period.between(LocalDate.of(2008, 9, 9), LocalDate.now()).getYears(), breed3, "P?mlx7MHz+#G");

        dog1 = new Animal(animalsProperties.getDogNames().get(2), BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.now().minusYears(10), dogType, (short) Period.between(LocalDate.now().minusYears(10), LocalDate.now()).getYears(), breed5, "DfEK[a!XM#2,");

        wolf1 = new Animal(animalsProperties.getWolfNames().get(0), BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.now().minusYears(10).plusDays(1), wolfType, (short) Period.between(LocalDate.now().minusYears(10).plusDays(1), LocalDate.now()).getYears(), breed12, "WjWP-R!59VM&");
        wolf2 = new Animal(animalsProperties.getWolfNames().get(1), BigDecimal.valueOf(700000.157).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.of(1997, 2, 1), wolfType, (short) Period.between(LocalDate.of(1997, 2, 1), LocalDate.now()).getYears(), breed10, "n:C^=3vu_8zk");

        shark1 = new Animal(animalsProperties.getSharkNames().get(1), BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1996, 6, 13), sharkType, (short) Period.between(LocalDate.of(1996, 6, 13), LocalDate.now()).getYears(), breed9, "1#Qw8#3u#U$^");
    }

    @DisplayName("Test methods for creating animals")
    @ParameterizedTest(name = "Test {arguments}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void starterTest(int value) {
        initAnimals();
        animals = new ArrayList<>();
        switch (value) {
            case 0:
                animals = List.of(cat1, cat3, cat2, dog1, wolf1, wolf2, shark1);
                break;
            case 1:
                animals = List.of(cat1, cat3, cat2, wolf1, wolf2);
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
        doAnswer(invocation -> {
            Transaction transaction = DBService.getTransaction();

            if (value == 2) {
                assertThrows(IllegalArgumentException.class, () -> {
                    animalDAO.saveListOfAnimals(finalAnimals);
                });
            } else if (value == 4) {
                assertThrows(NullPointerException.class, () -> {
                    animalDAO.saveListOfAnimals(finalAnimals);
                });
            } else {
                animalDAO.saveListOfAnimals(finalAnimals);
                transaction.commit();
            }
            return null;
        }).when(createAnimalService).createAnimals();

        if (value == 2 || value == 4) {
            createAnimalServiceBeanPostProcessor.postProcessAfterInitialization(createAnimalService, "createAnimalService");
            Transaction transaction = DBService.getTransaction();
            assertNotEquals(animals, animalDAO.getListOfAnimals());
            transaction.commit();
        } else {
            createAnimalServiceBeanPostProcessor.postProcessAfterInitialization(createAnimalService, "createAnimalService");
            Transaction transaction = DBService.getTransaction();
            assertEquals(animals, animalDAO.getListOfAnimals());
            transaction.commit();
        }

        Transaction transaction = DBService.getTransaction();
        animalDAO.deleteAll();
        transaction.commit();
    }

    @Configuration
    @ComponentScan
    static class SpringBootStarterTestConfig {
        @Bean
        public CreateAnimalServiceBeanPostProcessor createAnimalServiceBeanPostProcessor() {
            return new CreateAnimalServiceBeanPostProcessor();
        }

        @Bean
        public AnimalDAO animalDAO() {
            return new AnimalDAO();
        }

        @Bean
        public AnimalTypeDAO animalTypeDAO() {
            return new AnimalTypeDAO();
        }

        @Bean
        public BreedDAO breedDAO() {
            return new BreedDAO();
        }

        @Bean
        public ApplicationContextProvider applicationContextProvider() {
            return new ApplicationContextProvider();
        }
    }
}
