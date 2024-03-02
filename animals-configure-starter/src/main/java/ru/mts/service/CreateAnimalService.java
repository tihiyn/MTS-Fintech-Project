package ru.mts.service;

import ru.mts.model.Animal;
import ru.mts.model.AnimalEnum;

import java.util.List;
import java.util.Map;

public interface CreateAnimalService {
    List<String> breeds = List.of("Британская", "Шотландская", "Сфинкс", "Немецкая овчарка", "Доберман", "Лабрадор", "Тигровая", "Белая", "Молот", "Полярный", "Ньюфаундлендский", "Японский");

    List<String> characters = List.of("Добрый", "Злой", "Игривый", "Вредный", "Верный", "Пугливый");

    /**
     * Метод для определения типов животных. Внутри вызвается метод для создания животных
     *
     * @author Nikita
     * @since 1.3
     */
    void defineTypeOfAnimals();

    /**
     * Метод для создания животных при помощи цикла do-while
     *
     * @return HashMap с созданными животными
     * @author Nikita
     * @since 1.5
     */
    Map<AnimalEnum, List<Animal>> createAnimals();

    /**
     * Метод для получения списка типов животных
     *
     * @return список типов созданных животных
     * @author Nikita
     * @since 1.4
     */
    List<AnimalEnum> receiveAnimalTypes();

    /**
     * Метод для получения созданных животных
     *
     * @return HashMap из созданных животных
     * @author Nikita
     * @since 1.4
     */
    Map<AnimalEnum, List<Animal>> receiveCreatedAnimals();
}
