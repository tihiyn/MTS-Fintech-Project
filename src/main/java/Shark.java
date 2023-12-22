import java.math.BigDecimal;

public class Shark extends Predator {
    // голос акулы
    private static final String voice = "Буль";

    public Shark(String breed, String name, BigDecimal cost, String character) {
        this.breed = breed;
        this.name = name;
        this.cost = cost;
        this.character = character;
    }

    // релизация геттера getVoice()
    public String getVoice() {
        return voice;
    }
}