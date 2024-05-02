package ru.mts.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.mts.model.Animal;

import java.io.IOException;
import java.util.Base64;

public class AnimalSerializer extends StdSerializer<Animal> {
    public AnimalSerializer() {
        this(null);
    }

    public AnimalSerializer(Class<Animal> t) {
        super(t);
    }

    @Override
    public void serialize(Animal value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("name", value.getName());
        gen.writeStringField("cost", value.getCost().toString());
        gen.writeStringField("character", value.getCharacter());
        gen.writeStringField("birthDate", value.getBirthDate().toString());
        gen.writeObjectField("animalType", value.getAnimalType());
        gen.writeNumberField("age", value.getAge());
        gen.writeObjectField("breed", value.getBreed());
        gen.writeStringField("secretInformation", Base64.getEncoder().encodeToString(value.getSecretInformation().getBytes()));
        gen.writeEndObject();
    }
}
