package ru.mts;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mts.model.Animal;
import ru.mts.repository.AnimalsRepository;
import ru.mts.repository.AnimalsRepositoryImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class Scheduler {
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
        System.out.println("Names of animals that were born in a leap year: ");
        for (Map.Entry<String, LocalDate> node : animalsRepository.findLeapYearNames().entrySet()) {
            System.out.format("Name: %s, Birth Date: %s %n", node.getKey(), node.getValue());
        }
        System.out.println();

        int N = 25;
        System.out.format("Names of animals that are more than %d y.o.: %n", N);
        for (Map.Entry<Animal, Integer> node : animalsRepository.findOlderAnimal(N).entrySet()) {
            System.out.format("Name: %s, Age: %d %n", node.getKey().getName(), node.getValue());
        }
        System.out.println();

//        for (Map.Entry<String, List<Animal>> node : animalsRepository.findDuplicate().entrySet()) {
//            if (node.getValue() > 0) {
//                System.out.format("Type: %s, Num of Duplicates: %d %n", node.getKey(), node.getValue());
//            }
//        }
//        System.out.println();

        System.out.format("Average animal age: %.2f %n", animalsRepository.findAverageAge());
        System.out.println();

        System.out.println("Old and expensive: ");
        for (Animal animal: animalsRepository.findOldAndExpensive()) {
            System.out.println(animal.getName());
        }
        System.out.println();

        System.out.println("Min cost animals: ");
        for (String animalsName: animalsRepository.findMinCostAnimals()) {
            System.out.println(animalsName);
        }
    }
}
