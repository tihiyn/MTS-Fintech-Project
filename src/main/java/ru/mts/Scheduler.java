package ru.mts;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mts.exceptions.IllegalCollectionSizeException;
import ru.mts.exceptions.NegativeArgumentException;
import ru.mts.model.Animal;
import ru.mts.service.AnimalService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Scheduler {
    private static Logger logger = LoggerFactory.getLogger(Scheduler.class);

    private ObjectMapper objectMapper;
    private AnimalService animalService;
    private ObjectFactory<AnimalService> animalServiceObjectFactory;
    private Flyway flyway;

    public Scheduler(ObjectMapper objectMapper, ObjectFactory<AnimalService> animalServiceObjectFactory, Flyway flyway) {
        this.objectMapper = objectMapper;
        this.animalServiceObjectFactory = animalServiceObjectFactory;
        this.flyway = flyway;
    }

    /**
     * PostConstruct метод, который создаёт 2 потока с помощью ScheduledExecutorService и задаёт им имена, используя ThreadFactory.
     * Первый поток раз в 10 секунд вызывает метод findDuplicate из AnimalsRepositoryImpl.
     * Второй поток раз в 20 секунд вызывает метод findAverageAge из AnimalsRepositoryImpl.
     *
     * @author Nikita
     * @since 1.8
     */
    @PostConstruct
    public void runThreads() {
        logger.info("Hello from runThreads");
        flyway.migrate();
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2, new NamedThreadFactory());
        service.scheduleWithFixedDelay(() -> {
            Path path = Paths.get("src/main/resources/results/findDuplicate.json");
            File file = new File(path.toString());

            animalServiceObjectFactory.getObject().findDuplicate();

            try {
                Map<String, List<Animal>> duplicateMap = objectMapper.readValue(file, new TypeReference<>() {});
                logger.info("Duplicates of animals");
                for (Map.Entry<String, List<Animal>> node : duplicateMap.entrySet()) {
                    logger.info("Type: {}, Duplicates: {}", node.getKey(), node.getValue());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 0, 10, TimeUnit.SECONDS);

        service.scheduleWithFixedDelay(() -> {
            Path path = Paths.get("src/main/resources/results/findAverageAge.json");
            File file = new File(path.toString());

            animalServiceObjectFactory.getObject().findAverageAge();

            try {
                BigDecimal averageAge = objectMapper.readValue(file, BigDecimal.class);
                logger.info("Average animal age: {}", averageAge);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 0, 20, TimeUnit.SECONDS);
    }

    static class NamedThreadFactory implements ThreadFactory {
        private final AtomicInteger threadsCounter = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "MyThread-" + threadsCounter.incrementAndGet());
        }
    }

    /**
     * Метод, который раз в 60 секунд будет делать вызов методов
     * AnimalsRepository и выводить результаты в лог
     *
     * @author Nikita
     * @since 1.1
     */
    @Scheduled(fixedRate = 30000)
    public void printResults() {
        try {
//            flyway.baseline();
            animalService = animalServiceObjectFactory.getObject();

            Path path = Paths.get("src/main/resources/results/findLeapYearNames.json");
            File file = new File(path.toString());

            animalService.findLeapYearNames();
            Map<String, LocalDate> leapYearNamesMap = objectMapper.readValue(file, new TypeReference<>() {});

            logger.info("Names of animals that were born in a leap year");
            for (Map.Entry<String, LocalDate> node : leapYearNamesMap.entrySet()) {
                logger.info("Name: {}, Birth Date: {}", node.getKey(), node.getValue());
            }


            path = Paths.get("src/main/resources/results/findOlderAnimal.json");
            file = new File(path.toString());

            int N = 25;
            animalService.findOlderAnimal(N);

            Map<Animal, Short> olderAnimalMap = objectMapper.readValue(file, new TypeReference<>() {});

            logger.info("Names of animals that are more than {} y.o.", N);
            for (Map.Entry<Animal, Short> node : olderAnimalMap.entrySet()) {
                logger.info("{}, Age: {}", node.getKey(), node.getValue());
            }


//            path = Paths.get("src/main/resources/results/findDuplicate.json");
//            file = new File(path.toString());
//
//            animalsRepository.findDuplicate();
//
//            Map<String, List<Animal>> duplicateMap = objectMapper.readValue(file, new TypeReference<>() {});
//
//            logger.info("Duplicates of animals");
//            for (Map.Entry<String, List<Animal>> node : duplicateMap.entrySet()) {
//                logger.info("Type: {}, Duplicates: {}", node.getKey(), node.getValue());
//            }


//            path = Paths.get("src/main/resources/results/findAverageAge.json");
//            file = new File(path.toString());
//
//            animalsRepository.findAverageAge();
//
//            BigDecimal averageAge = objectMapper.readValue(file, BigDecimal.class);
//
//            logger.info("Average animal age: {}", averageAge);


            path = Paths.get("src/main/resources/results/findOldAndExpensive.json");
            file = new File(path.toString());

            animalService.findOldAndExpensive();

            List<Animal> oldAndExpensiveList = objectMapper.readValue(file, new TypeReference<>() {});

            logger.info("Old and expensive");
            for (Animal animal : oldAndExpensiveList) {
                logger.info(animal.toString());
            }


            path = Paths.get("src/main/resources/results/findMinCostAnimals.json");
            file = new File(path.toString());

            animalService.findMinCostAnimals();

            List<String> minCostAnimalsList = objectMapper.readValue(file, new TypeReference<>() {});

            logger.info("Min cost animals");
            for (String animalsName : minCostAnimalsList) {
                logger.info(animalsName);
            }
//            flyway.clean();
        } catch (NegativeArgumentException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalCollectionSizeException e) {
            logger.warn(e.getMessage());
        }
    }

//    /**
//     * Метод, который раз в 40 секунд создаёт объекты класса Creature из БД при помощи JdbcTemplate и выводит их в лог.
//     * Созданные животные сохраняются в ArrayList.
//     *
//     * @author Nikita
//     * @since 1.16
//     */
//    @Scheduled(fixedRate = 40000)
//    public void jdbcCreating() {
//        List<Animal> animals = creatureDAO.listCreatures();
//
//        animals.forEach(animal -> logger.info(animal.toString()));
//
////        logger.info("Hello from jdbcCreating");
//    }
}
