package ru.mts.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mts.model.Animal;
import ru.mts.model.AnimalType;
import ru.mts.model.Breed;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Class for testing method equals from Animal")
public class AnimalTest {
    AnimalType catType, dogType, wolfType, sharkType;
    List<String> breeds = List.of("Британская", "Шотландская", "Сфинкс", "Немецкая овчарка", "Доберман", "Лабрадор", "Тигровая", "Белая", "Молот", "Полярный", "Ньюфаундлендский", "Японский");

    @Test
    @DisplayName("Test override method equals")
    public void equals() {
        catType = new AnimalType("cat", false);
        dogType = new AnimalType("dog", false);
        wolfType = new AnimalType("wolf", true);
        sharkType = new AnimalType("shark", true);

        Breed breed1 = new Breed(breeds.get(0), catType);
        Breed breed2 = new Breed(breeds.get(1), catType);
        Breed breed3 = new Breed(breeds.get(2), catType);
        catType.setBreeds(new ArrayList<>(List.of(breed1, breed2, breed3)));

        Breed breed4 = new Breed(breeds.get(3), dogType);
        Breed breed5 = new Breed(breeds.get(4), dogType);
        Breed breed6 = new Breed(breeds.get(5), dogType);
        dogType.setBreeds(new ArrayList<>(List.of(breed4, breed5, breed6)));

        Breed breed7 = new Breed(breeds.get(6), sharkType);
        Breed breed8 = new Breed(breeds.get(7), sharkType);
        Breed breed9 = new Breed(breeds.get(8), sharkType);
        sharkType.setBreeds(new ArrayList<>(List.of(breed7, breed8, breed9)));

        Breed breed10 = new Breed(breeds.get(9), wolfType);
        Breed breed11 = new Breed(breeds.get(10), wolfType);
        Breed breed12 = new Breed(breeds.get(11), wolfType);
        sharkType.setBreeds(new ArrayList<>(List.of(breed10, breed11, breed12)));

        // создание животных
        Animal cat1 = new Animal("Миса", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.now().minusYears(10).minusDays(1), catType, (short) Period.between(LocalDate.now().minusYears(10).minusDays(1), LocalDate.now()).getYears(), breed1, "hSE{FD0T_a97");
        Animal dog = new Animal("Бобик", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.now().minusYears(10), dogType, (short) Period.between(LocalDate.now().minusYears(10), LocalDate.now()).getYears(), breed5, "DfEK[a!XM#2,");
        Animal cat2 = new Animal("Лёлик", BigDecimal.valueOf(12000.05).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 4, 18), catType, (short) Period.between(LocalDate.of(2013, 4, 18), LocalDate.now()).getYears(), breed2, "J4x№PS}mp6cu");
        Animal anotherDog = new Animal("Дружок", BigDecimal.valueOf(32000.33).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2020, 2, 15), dogType, (short) Period.between(LocalDate.of(2020, 2, 15), LocalDate.now()).getYears(), breed6, "O:n~(I22.;!K");
        Animal sameCat1 = new Animal("Миса", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.now().minusYears(10).minusDays(1), catType, (short) Period.between(LocalDate.now().minusYears(10).minusDays(1), LocalDate.now()).getYears(), breed1, "hSE{FD0T_a97");
        Animal stillCat1 = cat1;

        assertNotEquals(cat1, dog);
        assertNotEquals(cat1, cat2);
        assertEquals(cat1, sameCat1);
        assertEquals(cat1, stillCat1);
        assertEquals(sameCat1, stillCat1);
        assertEquals(cat1, cat1);
        assertNotEquals(anotherDog, null);

        stillCat1 = null;
        assertEquals(stillCat1, stillCat1);
        assertEquals(stillCat1, null);
        assertNotEquals(stillCat1, sameCat1);
        sameCat1 = null;
        assertEquals(stillCat1, sameCat1);
    }
}
