package ru.mts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mts.model.Animal;
import ru.mts.service.AnimalService;

import java.util.List;

@RestController
@RequestMapping("/rest-api")
@RequiredArgsConstructor
public class AnimalRESTController {
    private final AnimalService animalService;

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createAnimal(@RequestBody Animal animal) {
        animalService.saveAnimals(List.of(animal));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public List<Animal> getAnimals() {
        return animalService.getAllAnimals();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAnimal(@PathVariable("id") int id) {
        animalService.deleteAnimalById(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
