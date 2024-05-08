package ru.mts.service;

import java.util.List;

public interface CreateAnimalService {
    List<String> breeds = List.of("Британская", "Шотландская", "Сфинкс", "Немецкая овчарка", "Доберман", "Лабрадор", "Тигровая", "Белая", "Молот", "Полярный", "Ньюфаундлендский", "Японский");

    List<String> characters = List.of("Добрый", "Злой", "Игривый", "Вредный", "Верный", "Пугливый");


    /**
     * Метод для создания животных при помощи цикла do-while
     *
     * @return ConcurrentHashMap с созданными животными
     * @author Nikita
     * @since 1.5
     */
    void createAnimals();

    void initDB();
}
