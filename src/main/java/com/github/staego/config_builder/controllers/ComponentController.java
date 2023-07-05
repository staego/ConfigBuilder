package com.github.staego.config_builder.controllers;

import com.github.staego.config_builder.models.Component;
import com.github.staego.config_builder.repositories.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/component")
public class ComponentController {
    private static final String REDIRECT = "redirect:/component";
    private static final String TEMPLATE_PATH = "component/";
    private final ComponentRepository componentRepository;

    @Autowired
    public ComponentController(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("components", componentRepository.findAll());
        return TEMPLATE_PATH + "index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("component", new Component());
        return TEMPLATE_PATH + "add";
    }

    @PostMapping
    public String add(@ModelAttribute("component") Component component) {
        if (component.getTitle().isBlank()) {
            component.setTitle(null);
        }
        componentRepository.save(component);
        return REDIRECT;
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Component> component = componentRepository.findById(id);
        if (component.isEmpty()) {
            return REDIRECT;
        }
        model.addAttribute("component",component.get());
        return TEMPLATE_PATH + "edit";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("component") Component component) {
        if (component.getTitle().isBlank()) {
            component.setTitle(null);
        }
        componentRepository.save(component);
        return REDIRECT;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        Optional<Component> component = componentRepository.findById(id);
        if (component.isEmpty()) {
            return REDIRECT;
        }
        model.addAttribute("component", component.get());
        return TEMPLATE_PATH + "delete";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        Optional<Component> component = componentRepository.findById(id);
        if (component.isEmpty()) {
            return REDIRECT;
        }
        if (component.get().getTemplates().isEmpty()) {
            componentRepository.deleteById(id);
        } else {
            Component newComponent = component.get();
            newComponent.setTitle(null);
            componentRepository.save(newComponent);
        }
        return REDIRECT;
    }
}
