package ru.mts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mts.dto.AnimalDTO;
import ru.mts.model.Animal;
import ru.mts.service.AnimalService;

import java.util.List;

@Controller
@RequestMapping()
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
    public String newPerson(@ModelAttribute("animalDTO") Animal animalDTO) {
        return "animals/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("animalDTO") AnimalDTO animalDTO) {
        animalService.saveAnimals(List.of(animalDTO));
        return "redirect:/index";
    }
}
