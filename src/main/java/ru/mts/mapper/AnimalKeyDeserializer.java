package ru.mts.mapper;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import ru.mts.model.Animal;
import ru.mts.model.AnimalType;
import ru.mts.model.Breed;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;

public class AnimalKeyDeserializer extends KeyDeserializer {
    @Override
    public Object deserializeKey(String json, DeserializationContext ctxt) throws IOException {
        String name = json.substring(json.indexOf("name:") + 6, json.indexOf(",", json.indexOf("name:")) - 1);
        BigDecimal cost = new BigDecimal(json.substring(json.indexOf("cost:") + 5, json.indexOf(",", json.indexOf("cost:"))));
        String character = json.substring(json.indexOf("character:") + 11, json.indexOf(",", json.indexOf("character:")) - 1);
        LocalDate birthDate = LocalDate.parse(json.substring(json.indexOf("birthDate:") + 10, json.indexOf(",", json.indexOf("birthDate:"))));

        int animalTypeId = Integer.parseInt(json.substring(json.indexOf("AnimalType{id:") + 14, json.indexOf(",", json.indexOf("AnimalType{id:"))));
        String animalTypeType = json.substring(json.indexOf("type:") + 6, json.indexOf(",", json.indexOf("type:")) - 1);
        boolean animalTypeIsWild = Boolean.parseBoolean(json.substring(json.indexOf("isWild:") + 7, json.indexOf("}", json.indexOf("isWild:"))));
        AnimalType animalType = new AnimalType(animalTypeType, animalTypeIsWild);
        animalType.setId(animalTypeId);

        short age = Short.parseShort(json.substring(json.indexOf("age:") + 4, json.indexOf(",", json.indexOf("age:"))));

        int breedId = Integer.parseInt(json.substring(json.indexOf("Breed{id:") + 9, json.indexOf(",", json.indexOf("Breed{id:"))));
        String breedBreed = json.substring(json.indexOf("breed:'") + 7, json.indexOf("}", json.indexOf("breed:'")) - 1);
        Breed breed = new Breed(breedBreed, animalType);
        breed.setId(breedId);

        String secretInformation = new String(Base64.getDecoder().decode(json.substring(json.indexOf("secretInformation:") + 19, json.indexOf("}", json.indexOf("secretInformation:")) - 1)));

        return new Animal(name, cost, character, birthDate, animalType, age, breed, secretInformation);
    }
}
