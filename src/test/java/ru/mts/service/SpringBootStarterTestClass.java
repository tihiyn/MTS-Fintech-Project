package ru.mts.service;

import org.junit.jupiter.api.BeforeEach;
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
import ru.mts.AnimalsProperties;
import ru.mts.bpp.CreateAnimalServiceBeanPostProcessor;
import ru.mts.model.*;
import ru.mts.repository.AnimalsRepositoryImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@DisplayName("Class for testing animals-configure-starter")
@SpringBootTest
@Profile("test")
public class SpringBootStarterTestClass {
    Animal cat, dog, wolf, shark, anotherCat, anotherDog;
    Animal[] animals;
    private AnimalsRepositoryImpl animalsRepository;
    @Autowired
    private CreateAnimalServiceBeanPostProcessor createAnimalServiceBeanPostProcessor;
    @Autowired
    private AnimalsProperties animalsProperties;
    @MockBean
    private CreateAnimalServiceImpl createAnimalService;

    @BeforeEach
    public void init() {
        animalsRepository = new AnimalsRepositoryImpl(createAnimalService);

        // изменение поведения MockBean
        when(createAnimalService.getAnimalType()).thenCallRealMethod();
        when(createAnimalService.getAnimalsArray()).thenCallRealMethod();
        doCallRealMethod().when(createAnimalService).defineTypeOfAnimals();

        // создание животных
        cat = new Cat("Британская", animalsProperties.getCatNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2100, 7, 26));
        dog = new Dog("Доберман", animalsProperties.getDogNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.of(2000, 12, 1));
        wolf = new Wolf("Японский", animalsProperties.getWolfNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.of(1998, 3, 24));
        shark = new Shark("Молот", animalsProperties.getSharkNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(2050, 6, 13));
        anotherCat = new Cat("Сфинкс", animalsProperties.getCatNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2022, 1, 5));
        anotherDog = new Dog("Немецкая овчарка", animalsProperties.getDogNames().get(ThreadLocalRandom.current().nextInt(3)), BigDecimal.valueOf(15000).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2015, 10, 12));

    }

    @DisplayName("Test methods for creating animals")
    @ParameterizedTest(name = "Test {arguments}")
    @ValueSource(ints = {0, 1, 2, 3})
    public void starterTest(int value) {
        switch (value) {
            case 0:
                // корректный вариант
                animals = new Animal[]{cat, dog, wolf, shark};
                break;
            case 1:
                // корректный вариант
                animals = new Animal[]{shark, anotherCat, anotherDog};
                break;
            case 2:
                // вариант, когда массив содержит null-значения
                animals = new Animal[]{cat, anotherCat, null};
                break;
            case 3:
                // вариант, когда массив равен null
                animals = null;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }

        when(createAnimalService.createAnimals(anyInt())).thenReturn(animals);
        if (value == 2 || value == 3) {
            assertThrows(NullPointerException.class, () -> {
                createAnimalServiceBeanPostProcessor.postProcessAfterInitialization(createAnimalService, "createAnimalService");
            });
        } else {
            createAnimalServiceBeanPostProcessor.postProcessAfterInitialization(createAnimalService, "createAnimalService");
        }
        animalsRepository.fillStorage();
        if (value == 0 || value == 1) {
            assertArrayEquals(animals, animalsRepository.getAnimalsArray());
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
