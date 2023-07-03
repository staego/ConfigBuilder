package com.github.staego.config_builder.controllers;

import com.github.staego.config_builder.models.Template;
import com.github.staego.config_builder.repositories.TemplateRepository;
import com.github.staego.config_builder.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RootController {
    VendorRepository vendorRepository;
    TemplateRepository templateRepository;

    @Autowired
    public RootController(VendorRepository vendorRepository, TemplateRepository templateRepository) {
        this.vendorRepository = vendorRepository;
        this.templateRepository = templateRepository;
    }

    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("vendors", vendorRepository.findAll());
        model.addAttribute("path", "");
        return "category";
    }

    @GetMapping
    public String index(@RequestParam(value = "filter", required = false) String filter, Model model) {
        List<Template> templates;
        String filter_title;

        if (filter == null) {
            templates = templateRepository.findAll();
            filter_title = "Все";
        } else {
            templates = vendorRepository.findByName(filter).get(0).getTemplates();
            filter_title = vendorRepository.findByName(filter).get(0).getTitle();
        }
        model.addAttribute("templates", templates);
        model.addAttribute("filter_title", filter_title);
        return "index";
    }

    @GetMapping("/builder/{id}")
    public String builder(@PathVariable("id") int id, Model model) {
        return "builder";
    }

    @PostMapping("/build")
    public String build(Model model) {
        return "build";
    }
}
