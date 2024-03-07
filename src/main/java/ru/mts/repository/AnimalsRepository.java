package ru.mts.repository;

import ru.mts.model.Animal;
import ru.mts.model.AnimalEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * @author Nikita
     * @since 1.1
     */
    Map<Animal, Integer> findOlderAnimal(int N);

    /**
     * Метод ищет дубликаты среди животных.
     * Внутри вызывается метод для логирования.
     *
     * @return Map<String, Integer>, где String - тип животного, Integer - кол-во дубликатов данного типа
     * @author Nikita
     * @since 1.1
     */
    Map<String, List<Animal>> findDuplicate();

    /**
     * Метод вычисляет средний возраст всех животных.
     *
     * @return средний возраст всех животных
     * @author Nikita
     * @since 1.6
     */
    double findAverageAge();

    /**
     * Метод ищет всех животных, возраст которых больше 5 лет и
     * стоимость которых больше средней стоимости всех животных
     *
     * @return список животных
     * @author Nikita
     * @since 1.6
     */
    List<Animal> findOldAndExpensive();

    /**
     * Метод ищет 3 животных с самой низкой ценой
     *
     * @return список имён животных, отсортированный в обратном алфавитном порядке
     * @author Nikita
     * @since 1.6
     */
    List<String> findMinCostAnimals();
}
