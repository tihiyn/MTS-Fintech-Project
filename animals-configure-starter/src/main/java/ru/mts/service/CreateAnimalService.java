package ru.mts.service;

public interface CreateAnimalService {
    // массив пород (по 3 для каждого животного)
    String[] breeds = new String[]{"Британская", "Шотландская", "Сфинкс", "Немецкая овчарка", "Доберман", "Лабрадор", "Тигровая", "Белая", "Молот", "Полярный", "Ньюфаундлендский", "Японский"};

    // массив характеров (по 3 для каждого животного)
    String[] characters = new String[]{"Добрый", "Злой", "ИгривВредный", "Верный", "Пугливый"};
}
