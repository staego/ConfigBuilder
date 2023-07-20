package com.github.staego.config_builder.controllers;

import com.github.staego.config_builder.models.Component;
import com.github.staego.config_builder.services.ComponentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/component")
public class ComponentController {
    private static final String REDIRECT = "redirect:/component";
    private static final String TEMPLATE_PATH = "component/";
    private final ComponentService componentService;

    @Autowired
    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("components", componentService.findAll());
        return TEMPLATE_PATH + "index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("component", new Component());
        return TEMPLATE_PATH + "add";
    }

    @PostMapping
    public String add(@ModelAttribute("component") @Valid Component component, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return TEMPLATE_PATH + "add";
        }
        componentService.save(component);
        return REDIRECT;
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("component", componentService.findById(id));
        return TEMPLATE_PATH + "edit";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("component") @Valid Component component, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return TEMPLATE_PATH + "edit";
        }
        componentService.save(component);
        return REDIRECT;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        model.addAttribute("component", componentService.findById(id));
        return TEMPLATE_PATH + "delete";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        componentService.deleteById(id);
        return REDIRECT;
    }
}
