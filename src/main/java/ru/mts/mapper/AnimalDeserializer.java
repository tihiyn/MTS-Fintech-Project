package ru.mts.mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.mts.model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;

public class AnimalDeserializer extends JsonDeserializer<Animal> {
    @Override
    public Animal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String name = node.get("name").asText();
        BigDecimal cost = new BigDecimal(node.get("cost").asText());
        String character = node.get("character").asText();
        LocalDate birthDate = LocalDate.parse(node.get("birthDate").asText());

        JsonNode typeNode = node.get("animalType");
        ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
        AnimalType animalType = objectMapper.treeToValue(typeNode, AnimalType.class);

        short age = node.get("age").shortValue();

        JsonNode breedNode = node.get("breed");
        Breed breed = objectMapper.treeToValue(breedNode, Breed.class);
        breed.setAnimalType(animalType);

        String secretInformation = new String(Base64.getDecoder().decode(node.get("secretInformation").asText()));

        return new Animal(name, cost, character, birthDate, animalType, age, breed, secretInformation);
    }
}
