import java.math.BigDecimal;

public abstract class AbstractAnimal implements Animal {
    // порода животного
    protected String breed;
    // имя животного
    protected String name;
    // цена животного в магазине
    protected BigDecimal cost;
    // характер животного
    protected String character;
    // голос животного
    protected String voice;

    // реализация геттеров
    @Override
    public String getBreed() {
        return breed;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public String getCharacter() {
        return character;
    }
}
