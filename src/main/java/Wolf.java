import java.math.BigDecimal;

public class Wolf extends Predator {
    public Wolf(String breed, String name, BigDecimal cost, String character) {
        this.breed = breed;
        this.name = name;
        this.cost = cost;
        this.character = character;
        this.voice = "Аууууу";
    }

    // релизация геттера getVoice()
    public String getVoice() {
        return voice;
    }
}