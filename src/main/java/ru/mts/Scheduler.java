package ru.mts;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mts.exceptions.IllegalCollectionSizeException;
import ru.mts.exceptions.NegativeArgumentException;
import ru.mts.model.Animal;
import ru.mts.repository.AnimalsRepository;
import ru.mts.repository.AnimalsRepositoryImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class Scheduler {
    private static Logger logger = LoggerFactory.getLogger(Scheduler.class);

    private AnimalsRepository animalsRepository;

    public Scheduler(AnimalsRepositoryImpl animalsRepository) {
        this.animalsRepository = animalsRepository;
    }

    /**
     * Метод, который раз в минуту будет делать вызов методов
     * AnimalsRepository и выводить результаты в стандартный вывод
     *
     * @author Nikita
     * @since 1.1
     */
    @Scheduled(fixedRate = 60000)
    public void printResults() {
        try {
            logger.info("Names of animals that were born in a leap year");
            for (Map.Entry<String, LocalDate> node : animalsRepository.findLeapYearNames().entrySet()) {
                logger.info("Name: {}, Birth Date: {}", node.getKey(), node.getValue());
            }

            int N = 25;
            logger.info("Names of animals that are more than {} y.o.", N);
            for (Map.Entry<Animal, Integer> node : animalsRepository.findOlderAnimal(N).entrySet()) {
                logger.info("Name: {}, Age: {}", node.getKey().getName(), node.getValue());
            }

            logger.info("Duplicates of animals");
            for (Map.Entry<String, List<Animal>> node : animalsRepository.findDuplicate().entrySet()) {
                logger.info("Type: {}, Duplicates: {}", node.getKey(), node.getValue());
            }

            logger.info("Average animal age: {}", animalsRepository.findAverageAge());

            logger.info("Old and expensive");
            for (Animal animal : animalsRepository.findOldAndExpensive()) {
                logger.info(animal.getName());
            }

            logger.info("Min cost animals");
            for (String animalsName : animalsRepository.findMinCostAnimals()) {
                logger.info(animalsName);
            }
        } catch (NegativeArgumentException e) {
            logger.warn(e.getMessage());
        } catch (IllegalCollectionSizeException e) {
            logger.warn(e.getMessage());
        }
    }
}
