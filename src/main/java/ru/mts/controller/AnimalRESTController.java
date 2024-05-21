package ru.mts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mts.dto.AnimalDTO;
import ru.mts.service.AnimalService;

import java.util.List;

@RestController
@RequestMapping("/rest-api")
@RequiredArgsConstructor
public class AnimalRESTController {
    private final AnimalService animalService;

    @PostMapping("/add")
    public ResponseEntity<Void> createAnimal(@RequestBody AnimalDTO animalDTO) {
        animalService.saveAnimals(List.of(animalDTO));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public List<AnimalDTO> getAnimals() {
        return animalService.getAllAnimals();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable("id") int id) {
        animalService.deleteAnimalById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
