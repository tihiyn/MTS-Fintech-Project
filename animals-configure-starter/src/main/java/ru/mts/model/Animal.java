package ru.mts.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Animal {
    /**
     * Метод, который возвращает породу животного.
     *
     * @return порода животного.
     * @author Nikita
     * @since 1.0
     */
    String getBreed();

    /**
     * Метод, который возвращает имя животного.
     *
     * @return имя животного.
     * @author Nikita
     * @since 1.0
     */
    String getName();

    /**
     * Метод, который возвращает цену животного в магазине.
     *
     * @return цена животного в магазине.
     * @author Nikita
     * @since 1.0
     */
    BigDecimal getCost();

    /**
     * Метод, который возвращает характер животного.
     *
     * @return характер животного.
     * @author Nikita
     * @since 1.0
     */
    String getCharacter();

    /**
     * Метод, который возвращает голос животного.
     *
     * @return голос животного.
     * @author Nikita
     * @since 1.0
     */
    String getVoice();

    /**
     * Метод, который возвращает дату рождения животного.
     *
     * @return дата рождения животного.
     * @author Nikita
     * @since 1.0
     */
    LocalDate getBirthDate();
}
