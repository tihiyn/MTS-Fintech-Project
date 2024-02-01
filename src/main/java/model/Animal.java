package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Animal {
    /**
     * Метод, который возвращает породу животного.
     *
     * @return порода животного.
     * @since 1.0
     * @author Nikita
     */
    String getBreed();

    /**
     * Метод, который возвращает имя животного.
     *
     * @return имя животного.
     * @since 1.0
     * @author Nikita
     */
    String getName();

    /**
     * Метод, который возвращает цену животного в магазине.
     *
     * @return цена животного в магазине.
     * @since 1.0
     * @author Nikita
     */
    BigDecimal getCost();

    /**
     * Метод, который возвращает характер животного.
     *
     * @return характер животного.
     * @since 1.0
     * @author Nikita
     */
    String getCharacter();

    /**
     * Метод, который возвращает голос животного.
     *
     * @return голос животного.
     * @since 1.0
     * @author Nikita
     */
    String getVoice();

    /**
     * Метод, который возвращает дату рождения животного.
     *
     * @return дата рождения животного.
     * @since 1.0
     * @author Nikita
     */
    LocalDate getBirthDate();
}
