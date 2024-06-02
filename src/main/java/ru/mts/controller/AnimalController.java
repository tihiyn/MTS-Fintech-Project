package ru.mts.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mts.annotation.Logging;
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
    @Logging(value = "Index endpoint from AnimalController", entering = true, exiting = true)
    public String index(Model model) {
        model.addAttribute("animals", animalService.getAllAnimals());
        return "animals/index";
    }

    @DeleteMapping("/{id}")
    @Logging(value = "Delete endpoint from AnimalController", entering = true, exiting = true, level = "warn")
    @PreAuthorize("hasAuthority('USER')")
    public String delete(@PathVariable("id") int id) {
        animalService.deleteAnimalById(id);
        return "redirect:/index";
    }

    @GetMapping("/new")
    @Logging(value = "Create endpoint from AnimalController", entering = true, level = "info")
    @PreAuthorize("hasAuthority('USER')")
    public String newPerson(@ModelAttribute("animalDTO") Animal animalDTO) {
        return "animals/new";
    }

    @PostMapping()
    @Logging(value = "Create endpoint from AnimalController", exiting = true, level = "info")
    @PreAuthorize("hasAuthority('USER')")
    public String create(@ModelAttribute("animalDTO") AnimalDTO animalDTO) {
        animalService.saveAnimals(List.of(animalDTO));
        return "redirect:/index";
    }
}
