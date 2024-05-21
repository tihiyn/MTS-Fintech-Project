package ru.mts.controller;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.mts.mapper.dto.AnimalToAnimalDTOMapper;
import ru.mts.model.Animal;
import ru.mts.service.AnimalService;
import ru.mts.service.AnimalTypeService;
import ru.mts.service.BreedService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebAppConfiguration("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
class AnimalRESTControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private BreedService breedService;
    @Autowired
    private AnimalTypeService animalTypeService;
    @Autowired
    private AnimalToAnimalDTOMapper animalToAnimalDTOMapper;

    private Animal animal;

    @BeforeEach
    public void setup() {
        animal = new Animal();
        animal.setName("Kitty");
        animal.setCost(BigDecimal.valueOf(15000.75));
        animal.setCharacter("Игривый");
        animal.setBirthDate(LocalDate.of(2019, 3, 15));
        animal.setAnimalType(animalTypeService.getAnimalTypes().get(0));
        animal.setAge((short) 5);
        animal.setBreed(breedService.getBreeds().get(0));
        animal.setSecretInformation("password");
    }

    @Test
    void createAnimal() throws Exception {
        long beforeCall = animalService.countAnimals();

        mockMvc.perform(post("/rest-api/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"Kitty\",\n" +
                                "    \"cost\": 15000.75,\n" +
                                "    \"character\": \"Игривый\",\n" +
                                "    \"birthDate\": \"2019-03-15\",\n" +
                                "    \"animalType\": \"cat\",\n" +
                                "    \"age\": 5,\n" +
                                "    \"breed\": \"Сфинкс\",\n" +
                                "    \"secretInformation\": \"password\"\n" +
                                "}"))
                .andExpect(status().is(204));

        long afterCall = animalService.countAnimals();
        assertThat(afterCall, IsEqual.equalTo(beforeCall + 1));
        animalService.deleteAnimals();
    }

    @Test
    void getAnimals() throws Exception {
        List<Animal> animals = Collections.singletonList(animal);
        animalService.saveAnimals(animals.stream().map(animalToAnimalDTOMapper).collect(Collectors.toList()));

        mockMvc.perform(get("/rest-api"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Kitty"))
                .andExpect(jsonPath("$[0].cost").value(15000.75))
                .andExpect(jsonPath("$[0].character").value("Игривый"))
                .andExpect(jsonPath("$[0].birthDate[0]").value(2019))
                .andExpect(jsonPath("$[0].birthDate[1]").value(3))
                .andExpect(jsonPath("$[0].birthDate[2]").value(15))
                .andExpect(jsonPath("$[0].animalType").value("cat"))
                .andExpect(jsonPath("$[0].age").value(5))
                .andExpect(jsonPath("$[0].breed").value("Британская"))
                .andExpect(jsonPath("$[0].secretInformation").value("password"));

        animalService.deleteAnimals();
    }

    @Test
    void deleteAnimal() throws Exception {
        animalService.saveAnimals(Stream.of(animal).map(animalToAnimalDTOMapper).collect(Collectors.toList()));
        long id = animalService.getAllAnimals().get(0).getId();

        long beforeCall = animalService.countAnimals();
        mockMvc.perform(delete("/rest-api/" + id))
                .andExpect(status().is(204));

        long afterCall = animalService.countAnimals();
        assertThat(afterCall, IsEqual.equalTo(beforeCall - 1));
        animalService.deleteAnimals();
    }
}