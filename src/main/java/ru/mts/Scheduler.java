package ru.mts;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mts.model.Animal;
import ru.mts.repository.AnimalsRepository;
import ru.mts.repository.AnimalsRepositoryImpl;

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
        System.out.println("Имена животных, которые родились в високосный год: ");
        for (String animal : animalsRepository.findLeapYearNames()) {
            System.out.println(animal);
        }
        System.out.println();

        int N = 25;
        System.out.format("Имена животных, которым больше %d лет: \n", N);
        for (Animal animal : animalsRepository.findOlderAnimal(N)) {
            System.out.println(animal.getName());
        }
        System.out.println();

        animalsRepository.findDuplicate();
    }
}
