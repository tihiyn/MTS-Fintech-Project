package ru.mts.repository;

import ru.mts.model.Animal;

import java.util.Set;

public interface AnimalsRepository {
    /**
     * Геттер для массива животных
     *
     * @return массив животных
     * @author Nikita
     * @since 1.4
     */
    Animal[] getAnimalsArray();

    void fillStorage();

    /**
     * Метод ищет животных, родившихся в високосный год.
     *
     * @return массив из имён животных, родившихся в високосный год.
     * @author Nikita
     * @since 1.1
     */
    String[] findLeapYearNames();

    /**
     * Метод ищет животных, возраст которых больше N лет.
     *
     * @param N возрастная граница.
     * @return массив животных, возраст которых больше N лет.
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
