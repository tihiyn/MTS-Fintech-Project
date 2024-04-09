package ru.mts.mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.mts.model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;

public class AnimalDeserializer extends JsonDeserializer<Animal> {
    @Override
    public Animal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String type = node.get("type").asText();
        String breed = node.get("breed").asText();
        String name = node.get("name").asText();
        BigDecimal cost = new BigDecimal(node.get("cost").asText());
        String character = node.get("character").asText();
        LocalDate birthDate = LocalDate.parse(node.get("birthDate").asText());
        String secretInformation = new String(Base64.getDecoder().decode(node.get("secretInformation").asText()));

        return switch (type) {
            case "Cat" -> new Cat(breed, name, cost, character, birthDate, secretInformation);
            case "Dog" -> new Dog(breed, name, cost, character, birthDate, secretInformation);
            case "Wolf" -> new Wolf(breed, name, cost, character, birthDate, secretInformation);
            case "Shark" -> new Shark(breed, name, cost, character, birthDate, secretInformation);
            default -> null;
        };
    }
}
