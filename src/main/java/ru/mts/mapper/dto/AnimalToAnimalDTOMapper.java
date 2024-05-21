package ru.mts.mapper.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.dto.AnimalDTO;
import ru.mts.model.Animal;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class AnimalToAnimalDTOMapper implements Function<Animal, AnimalDTO> {

    @Override
    public AnimalDTO apply(Animal animal) {
        AnimalDTO animalDTO = new AnimalDTO(
//                animal.getId(),
                animal.getName(),
                animal.getCost(),
                animal.getCharacter(),
                animal.getBirthDate(),
                animal.getAnimalType().getType(),
                animal.getAge(),
                animal.getBreed().getBreed(),
                animal.getSecretInformation());
        animalDTO.setId(animal.getId());

        return animalDTO;
    }
}
