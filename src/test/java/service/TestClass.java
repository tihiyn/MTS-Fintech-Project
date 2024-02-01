package service;

import model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Outer class")
class TestClass {
    @Nested
    @DisplayName("Class for testing method equals from AbstractAnimal")
    public class FirstInnerClass {
        @Test
        @DisplayName("Test override method equals")
        public void equals() {
            // создание животных
            Animal cat1 = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2100, 7, 26));
            Animal dog = new Dog("Доберман", "Шарик", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.of(2000, 12, 1));
            Animal cat2 = new Cat("Сфинкс", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2400, 1, 5));
            Animal anotherDog = new Dog("Немецкая овчарка", "Жучка", BigDecimal.valueOf(15000).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 10, 12));
            Animal sameCat1 = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2100, 7, 26));
            Animal stillCat1 = cat1;

            assertNotEquals(cat1, dog);
            assertNotEquals(cat1, cat2);
            assertEquals(cat1, sameCat1);
            assertEquals(cat1, stillCat1);
            assertEquals(sameCat1, stillCat1);
            assertEquals(cat1, cat1);
        }
    }

    @Nested
    @DisplayName("Class for testing methods from SearchServiceImpl")
    public class SecondInnerClass {
        @Test
        @DisplayName("Test method findLeapYearNames")
        public void findLeapYearNames() {
            // создание объекта для поиска
            SearchService searchObject = new SearchServiceImpl();

            // создание животных
            Animal cat = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2100, 7, 26));
            Animal dog = new Dog("Доберман", "Шарик", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.of(2000, 12, 1));
            Animal wolf = new Wolf("Японский", "Волчок", BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.of(1998, 3, 24));
            Animal shark = new Shark("Молот", "Шарки", BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(2050, 6, 13));
            Animal anotherCat = new Cat("Сфинкс", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2400, 1, 5));
            Animal anotherDog = new Dog("Немецкая овчарка", "Жучка", BigDecimal.valueOf(15000).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2013, 10, 12));

            // исходный массив животных
            Animal[] inputArray = {cat, dog, wolf, shark, anotherCat, anotherDog};
            // массив имён животных, которые родились в високосном году
            String[] outputArray = {dog.getName(), anotherCat.getName()};

            assertArrayEquals(outputArray, searchObject.findLeapYearNames(inputArray));
        }

        /**
         * Своя реализация метода contains.
         * Метод проверяет, содержится ли в двумерном массиве объектов mainArray заданный массив subArray
         *
         * @param mainArray двумерный массив объектов
         * @param subArray заданный массив объектов
         * @return {@code true} если mainArray содержит subArray
         * @since 1.2
         * @author Nikita
         */
        public static boolean containsArray(Object[][] mainArray, Object[] subArray) {
            for (Object[] row : mainArray) {
                if (Arrays.deepEquals(row, subArray)) {
                    return true;
                }
            }
            return false;
        }

        @DisplayName("Test method findOlderAnimal")
        @ParameterizedTest(name = "Array of animals, more than {arguments} y.o.")
        @ValueSource(ints = {9, 5, 1})
        public void findOlderAnimal(int value) {
            // создание объекта для поиска
            SearchService searchObject = new SearchServiceImpl();

            // создание животных
            Animal cat = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2014, 1, 31));
            Animal dog = new Dog("Доберман", "Шарик", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.of(2014, 2, 1));
            Animal wolf = new Wolf("Японский", "Волчок", BigDecimal.valueOf(500000).setScale(2, RoundingMode.HALF_UP), "Игривый", LocalDate.of(2014, 2, 2));
            Animal shark = new Shark("Молот", "Шарки", BigDecimal.valueOf(1000000).setScale(2, RoundingMode.HALF_UP), "Пугливый", LocalDate.of(1995, 6, 13));
            Animal anotherCat = new Cat("Сфинкс", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Вредный", LocalDate.of(2022, 1, 5));
            Animal anotherDog = new Dog("Немецкая овчарка", "Жучка", BigDecimal.valueOf(15000).setScale(2, RoundingMode.HALF_UP), "Верный", LocalDate.of(2015, 10, 12));

            // исходный массив животных
            Animal[] inputArray = {cat, dog, wolf, shark, anotherCat, anotherDog};

            // двумерный массив имён животных, возраст которых больше value
            Animal[][] outputArrays = {
                    {cat, dog, shark},
                    {cat, dog, wolf, shark, anotherDog},
                    {cat, dog, wolf, shark, anotherCat, anotherDog}
            };

            assertTrue(containsArray(outputArrays, searchObject.findOlderAnimal(inputArray, value)));
        }

        @Test
        @DisplayName("Test method findDuplicate")
        public void findDuplicate() {
            // создание объекта для поиска
            SearchService searchObject = new SearchServiceImpl();

            // создание 3 животных, из которых 1 и 3 одинаковые
            Animal cat = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2015, 7, 26));
            Animal dog = new Dog("Доберман", "Шарик", BigDecimal.valueOf(25000).setScale(2, RoundingMode.HALF_UP), "Злой", LocalDate.of(2010, 12, 1));
            Animal sameCat = new Cat("Британская", "Барсик", BigDecimal.valueOf(10000).setScale(2, RoundingMode.HALF_UP), "Добрый", LocalDate.of(2015, 7, 26));

            // исходный массив животных
            Animal[] inputArray = {cat, dog, sameCat, cat, dog};
            // массив дубликатов
            Animal[] outputArray = {cat, dog};

            assertArrayEquals(outputArray, searchObject.findDuplicate(inputArray));
        }
    }
}
