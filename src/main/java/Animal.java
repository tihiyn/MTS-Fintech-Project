import java.math.BigDecimal;

public interface Animal {
    /**
     * Метод, который возвращает породу животного
     *
     * @Since: 1.0
     * @Author: Nikita
     */
    public String getBreed();

    /**
     * Метод, который возвращает имя животного
     *
     * @Since: 1.0
     * @Author: Nikita
     */
    public String getName();

    /**
     * Метод, который возвращает цену животного в магазине.
     * Значение округляется до 2 знаков после запятой.
     *
     * @Since: 1.0
     * @Author: Nikita
     */
    public BigDecimal getCost();

    /**
     * Метод, который возвращает характер животного
     *
     * @Since: 1.0
     * @Author: Nikita
     */
    public String getCharacter();

    /**
     * Метод, который возвращает голос животного
     *
     * @Since: 1.0
     * @Author: Nikita
     */
    public String getVoice();
}
