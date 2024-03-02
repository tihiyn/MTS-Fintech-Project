package ru.mts.repository;

import ru.mts.model.Animal;
import ru.mts.model.AnimalEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AnimalsRepository {
    /**
     * Геттер для животных из хранилища.
     *
     * @return животные из хранилища
     * @author Nikita
     * @since 1.4
     */
    Map<AnimalEnum, List<Animal>> getAnimalStorage();

    void fillStorage();

    /**
     * Метод ищет животных, родившихся в високосный год.
     *
     * @return Map<String, LocalDate>, где String - тип животного + его имя; LocalDate - дата рождения животного
     * @throws IllegalStateException если тип животного null
     * @author Nikita
     * @since 1.1
     */
    Map<String, LocalDate> findLeapYearNames();

    /**
     * Метод ищет животных, возраст которых больше N лет.
     * Если таких нет, то возвращается самый старший из животных.
     *
     * @param N возрастная граница.
     * @return Map<Animal, Integer>, где Animal - животное; Integer - его возраст
     * @throws IllegalStateException если тип животного null
     * @author Nikita
     * @since 1.1
     */
    Map<Animal, Integer> findOlderAnimal(int N);

    /**
     * Метод ищет дубликаты среди животных.
     * Внутри вызывается метод для логирования.
     *
     * @return Map<String, Integer>, где String - тип животного, Integer - кол-во дубликатов данного типа
     * @throws IllegalStateException если тип животного равен null или список животных равен null
     * @author Nikita
     * @since 1.1
     */
    Map<String, Integer> findDuplicate();
}
