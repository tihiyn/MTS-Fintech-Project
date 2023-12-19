import java.math.BigDecimal;

public class Shark extends Predator {
    public Shark(String breed, String name, BigDecimal cost, String character) {
        this.breed = breed;
        this.name = name;
        this.cost = cost;
        this.character = character;
        this.voice = "Буль";
    }

    // релизация геттера getVoice()
    public String getVoice() {
        return voice;
    }
}