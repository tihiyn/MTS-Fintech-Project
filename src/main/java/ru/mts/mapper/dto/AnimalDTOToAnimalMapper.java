package ru.mts.mapper.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.dto.AnimalDTO;
import ru.mts.model.Animal;
import ru.mts.service.AnimalTypeService;
import ru.mts.service.BreedService;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AnimalDTOToAnimalMapper implements Function<AnimalDTO, Animal> {
    private final AnimalTypeService animalTypeService;
    private final BreedService breedService;

    @Override
    public Animal apply(AnimalDTO animalDTO) {
        Animal animal = new Animal(
                animalDTO.getName(),
                animalDTO.getCost(),
                animalDTO.getCharacter(),
                animalDTO.getBirthDate(),
                animalTypeService.getAnimalTypeByType(animalDTO.getAnimalType()),
                animalDTO.getAge(),
                breedService.getBreedByBreed(animalDTO.getBreed()),
                animalDTO.getSecretInformation()
        );
//        animal.setId(animalDTO.getId());

        return animal;
    }

}
