package ru.mts.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.mts.model.Animal;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.findAndRegisterModules();
        objectMapper.registerModule(new JavaTimeModule());

        // Настройки сериализации/десериализации
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Отступы для красивого вывода
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // Пропускать неизвестные свойства

        SimpleModule module = new SimpleModule();
        module.addKeySerializer(Animal.class, new AnimalKeySerializer());
        module.addKeyDeserializer(Animal.class, new AnimalKeyDeserializer());
        module.addSerializer(Animal.class, new AnimalSerializer());
        module.addDeserializer(Animal.class, new AnimalDeserializer());
        objectMapper.registerModule(module);

//        SimpleModule module = new SimpleModule();
//        module.setSerializerModifier(new CustomMapSerializerModifier());
//        objectMapper.registerModule(module);

        return objectMapper;
    }
}

