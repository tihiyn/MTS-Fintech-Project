package ru.mts.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.mts.mapper.AnimalDeserializer;
import ru.mts.mapper.AnimalKeyDeserializer;
import ru.mts.mapper.AnimalKeySerializer;
import ru.mts.mapper.AnimalSerializer;
import ru.mts.model.Animal;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
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

        return objectMapper;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}

