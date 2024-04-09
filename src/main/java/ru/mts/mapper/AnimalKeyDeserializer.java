package ru.mts.mapper;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import ru.mts.model.Cat;
import ru.mts.model.Dog;
import ru.mts.model.Shark;
import ru.mts.model.Wolf;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;

public class AnimalKeyDeserializer extends KeyDeserializer {
    @Override
    public Object deserializeKey(String json, DeserializationContext ctxt) throws IOException {
        String type = json.substring(0, json.indexOf("{"));
        String breed = json.substring(json.indexOf("'breed':") + 9, json.indexOf(",", json.indexOf("'breed':")) - 1);
        String name = json.substring(json.indexOf("'name':") + 8, json.indexOf(",", json.indexOf("'name':")) - 1);
        BigDecimal cost = new BigDecimal(json.substring(json.indexOf("'cost':") + 7, json.indexOf(",", json.indexOf("'cost'"))));
        String character = json.substring(json.indexOf("'character':") + 13, json.indexOf(",", json.indexOf("'character':")) - 1);
        LocalDate birthDate = LocalDate.parse(json.substring(json.indexOf("'birthDate':") + 13, json.indexOf(",", json.indexOf("'birthDate':")) - 1));
        String secretInformation = new String(Base64.getDecoder().decode(json.substring(json.indexOf("'secretInformation':") + 21, json.indexOf("}", json.indexOf("'secretInformation':")) - 1)));

        return switch (type) {
            case "Cat" -> new Cat(breed, name, cost, character, birthDate, secretInformation);
            case "Dog" -> new Dog(breed, name, cost, character, birthDate, secretInformation);
            case "Wolf" -> new Wolf(breed, name, cost, character, birthDate, secretInformation);
            case "Shark" -> new Shark(breed, name, cost, character, birthDate, secretInformation);
            default -> null;
        };
    }
}
