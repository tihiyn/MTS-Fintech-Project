package ru.mts;


import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mts.exceptions.IllegalCollectionSizeException;
import ru.mts.exceptions.NegativeArgumentException;
import ru.mts.model.Animal;
import ru.mts.repository.AnimalsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class Scheduler {
    private static Logger logger = LoggerFactory.getLogger(Scheduler.class);

    private AnimalsRepository animalsRepository;
    private ObjectFactory<AnimalsRepository> animalsRepositoryObjectFactory;

    public Scheduler(ObjectFactory<AnimalsRepository> animalsRepositoryObjectFactory) {
        this.animalsRepositoryObjectFactory = animalsRepositoryObjectFactory;
    }

    @PostConstruct
    public void runThreads() {
        Thread thread1 = new Thread(() -> {
            while (true) {
                animalsRepository = animalsRepositoryObjectFactory.getObject();
                logger.info("Duplicates of animals");
                for (Map.Entry<String, List<Animal>> node : animalsRepository.findDuplicate().entrySet()) {
                    logger.info("Type: {}, Duplicates: {}", node.getKey(), node.getValue());
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread1.setName("printDuplicateTread");

        Thread thread2 = new Thread(() -> {
            while (true) {
                animalsRepository = animalsRepositoryObjectFactory.getObject();
                logger.info("Average animal age: {}", animalsRepository.findAverageAge());
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread2.setName("findAverageAgeTread");

        thread1.start();
        thread2.start();
    }

    /**
     * Метод, который раз в минуту будет делать вызов методов
     * AnimalsRepository и выводить результаты в лог
     *
     * @author Nikita
     * @since 1.1
     */
    @Scheduled(fixedRate = 20000)
    public void printResults() {
        System.out.println("Hello world");
//        try {
//            animalsRepository = animalsRepositoryObjectFactory.getObject();
//            logger.info("Names of animals that were born in a leap year");
//            for (Map.Entry<String, LocalDate> node : animalsRepository.findLeapYearNames().entrySet()) {
//                logger.info("Name: {}, Birth Date: {}", node.getKey(), node.getValue());
//            }
//
//            int N = 25;
//            logger.info("Names of animals that are more than {} y.o.", N);
//            for (Map.Entry<Animal, Integer> node : animalsRepository.findOlderAnimal(N).entrySet()) {
//                logger.info("Name: {}, Age: {}", node.getKey().getName(), node.getValue());
//            }
//
//            logger.info("Duplicates of animals");
//            for (Map.Entry<String, List<Animal>> node : animalsRepository.findDuplicate().entrySet()) {
//                logger.info("Type: {}, Duplicates: {}", node.getKey(), node.getValue());
//            }
//
//            logger.info("Average animal age: {}", animalsRepository.findAverageAge());
//
//            logger.info("Old and expensive");
//            for (Animal animal : animalsRepository.findOldAndExpensive()) {
//                logger.info(animal.getName());
//            }
//
//            logger.info("Min cost animals");
//            for (String animalsName : animalsRepository.findMinCostAnimals()) {
//                logger.info(animalsName);
//            }
//        } catch (NegativeArgumentException e) {
//            logger.warn(e.getMessage());
//        } catch (IllegalCollectionSizeException e) {
//            logger.warn(e.getMessage());
//        }
    }
}
