package ru.mts.repository;

import ru.mts.model.Animal;

import java.util.Set;

public interface AnimalsRepository {
    /**
     * Метод ищет животных, родившихся в високосный год.
     *
     * @return массив из имён животных, родившихся в високосный год.
     * @author Nikita
     * @since 1.1
     */
    String[] findLeapYearNames();

    /**
     * Метод ищет животных, возраст которых больще N лет.
     *
     * @param N возрастная граница.
     * @return массив животных, возраст которых больще N лет.
     * @author Nikita
     * @since 1.1
     */
    Animal[] findOlderAnimal(int N);

    /**
     * Метод ищет дубликаты в массиве животных.
     *
     * @return множество дубликатов животных.
     * @author Nikita
     * @since 1.1
     */
    Set<Animal> findDuplicate();
}
