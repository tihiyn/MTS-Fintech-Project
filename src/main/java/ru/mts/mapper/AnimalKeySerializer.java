package ru.mts.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.mts.model.Animal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AnimalKeySerializer extends JsonSerializer<Animal> {
    @Override
    public void serialize(Animal animal, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String pairToString = animal.toString();
        String secret = pairToString.substring(pairToString.indexOf("secretInformation:") + 19, pairToString.indexOf("'}", pairToString.indexOf("secretInformation:")));
        String encodedPair = pairToString.replace(secret, Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8)));
        gen.writeFieldName(encodedPair);
    }
}
