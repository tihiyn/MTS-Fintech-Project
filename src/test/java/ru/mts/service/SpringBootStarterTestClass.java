package ru.mts.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import ru.mts.AnimalsProperties;
import ru.mts.bpp.CreateAnimalServiceBeanPostProcessor;
import ru.mts.model.*;
import ru.mts.repository.AnimalsRepositoryImpl;
import ru.mts.repository.AnimalsRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@DisplayName("Class for testing animals-configure-starter")
@SpringBootTest
@ActiveProfiles("test")
public class SpringBootStarterTestClass {
    Animal cat1, cat2, cat3, dog1, wolf1, wolf2, shark1;
    Map<String, List<Animal>> animals;

    private AnimalsRepository animalsRepository;
    @Autowired
    private CreateAnimalServiceBeanPostProcessor createAnimalServiceBeanPostProcessor;
    @Autowired
    private AnimalsProperties animalsProperties;
    @MockBean
    private CreateAnimalServiceImpl createAnimalService;


    private void initAnimals() {
        cat1 = new Cat("Британская", animalsProperties.getCatNames().get(1), BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.now().minusYears(10).minusDays(1));
        cat2 = new Cat("Шотландская", animalsProperties.getCatNames().get(0), BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18));
        cat3 = new Cat("Сфинкс", animalsProperties.getCatNames().get(2), BigDecimal.valueOf(7000.2).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2008, 9, 9));

        dog1 = new Dog("Доберман", animalsProperties.getDogNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.now().minusYears(10));

        wolf1 = new Wolf("Японский", animalsProperties.getWolfNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.now().minusYears(10).plusDays(1));
        wolf2 = new Wolf("Полярный", animalsProperties.getWolfNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(700000.157).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.of(1997, 2, 1));

        shark1 = new Shark("Молот", animalsProperties.getSharkNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1996, 6, 13));
        }

    @DisplayName("Test methods for creating animals")
    @ParameterizedTest(name = "Test {arguments}")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
    public void starterTest(int value) {
        animalsRepository = new AnimalsRepositoryImpl(createAnimalService);

        // изменение поведения MockBean
        when(createAnimalService.receiveAnimalType()).thenCallRealMethod();
        when(createAnimalService.receiveCreatedAnimals()).thenCallRealMethod();
        doCallRealMethod().when(createAnimalService).defineTypeOfAnimals();

        initAnimals();
        animals = new HashMap<>(4);
        switch (value) {
            case 0:
                // корректный вариант
                animals.put("Cat", List.of(cat1, cat3, cat2));
                animals.put("Dog", List.of(dog1));
                animals.put("Wolf", List.of(wolf1, wolf2));
                animals.put("Shark", List.of(shark1));
                break;
            case 1:
                // корректный вариант c наличием пустых списков
                animals.put("Cat", List.of(cat1, cat3, cat2));
                animals.put("Wolf", List.of(wolf1, wolf2));
                animals.put("Shark", new ArrayList<>());
                break;
            case 2:
                // вариант, когда список содержит null-значения
                animals.put("Shark", List.of(shark1));
                animals.put("Dog", new ArrayList<>());
                animals.put("Wolf", Arrays.asList(wolf1, null));
                animals.put("Cat", new ArrayList<>());
                break;
            case 3:
                // вариант, когда все списки пустые
                animals.put("Cat", new ArrayList<>());
                animals.put("Dog", new ArrayList<>());
                animals.put("Wolf", new ArrayList<>());
                animals.put("Shark", new ArrayList<>());
                break;
            case 4:
                // вариант, когда value равно null
                animals.put("Cat", null);
                animals.put("Dog", new ArrayList<>());
                animals.put("Wolf", new ArrayList<>());
                animals.put("Shark", new ArrayList<>());
                break;
            case 5:
                // вариант, когда key равен null
                animals.put(null, new ArrayList<>());
                animals.put("Dog", new ArrayList<>());
                animals.put("Wolf", new ArrayList<>());
                animals.put("Shark", new ArrayList<>());
                break;
            case 6:
                // вариант, когда мапа равна null
                animals = null;
                break;
            case 7:
                // вариант, когда мапа пустая
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }

        when(createAnimalService.createAnimals()).thenReturn(animals);

        if (value == 4 || value == 5 || value == 6) {
            assertThrows(NullPointerException.class, () -> {
                createAnimalServiceBeanPostProcessor.postProcessAfterInitialization(createAnimalService, "createAnimalService");
            });
        }
        else {
            createAnimalServiceBeanPostProcessor.postProcessAfterInitialization(createAnimalService, "createAnimalService");
            animalsRepository.fillStorage();
            assertEquals(animals, animalsRepository.getAnimalsMap());
        }
    }

    @Configuration
    @ComponentScan
    static class SpringBootStarterTestConfig {
        @Bean
        public CreateAnimalServiceBeanPostProcessor createAnimalServiceBeanPostProcessor() {
            return new CreateAnimalServiceBeanPostProcessor();
        }
    }
}
