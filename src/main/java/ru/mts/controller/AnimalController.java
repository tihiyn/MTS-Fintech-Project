package ru.mts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mts.model.Animal;
import ru.mts.service.AnimalService;

import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("animals", animalService.getAllAnimals());
        return "animals/index";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        animalService.deleteAnimalById(id);
        return "redirect:/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("animal") Animal animal) {
        return "animals/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("animal") Animal animal) {
        animalService.saveAnimals(List.of(animal));
        return "redirect:/index";
    }
}
