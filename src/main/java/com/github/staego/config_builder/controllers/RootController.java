package com.github.staego.config_builder.controllers;

import com.github.staego.config_builder.models.Component;
import com.github.staego.config_builder.models.Template;
import com.github.staego.config_builder.repositories.ComponentRepository;
import com.github.staego.config_builder.repositories.TemplateRepository;
import com.github.staego.config_builder.repositories.VendorRepository;
import com.github.staego.config_builder.utils.TemplateEngine;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.StoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class RootController {
    VendorRepository vendorRepository;
    TemplateRepository templateRepository;
    ComponentRepository componentRepository;

    @Autowired
    public RootController(VendorRepository vendorRepository, TemplateRepository templateRepository, ComponentRepository componentRepository) {
        this.vendorRepository = vendorRepository;
        this.templateRepository = templateRepository;
        this.componentRepository = componentRepository;
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
        Optional<Template> template = templateRepository.findById(id);
        if (template.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("id", id);
        model.addAttribute("components", template.get().getComponents());
        return "builder";
    }

    @PostMapping("/build/{id}")
    public String build(@PathVariable("id") int id, HttpServletRequest request, Model model) {
//        request.getParameterMap()
        Map<String, String> map = request.getParameterMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]) );
        String result = TemplateEngine.render(templateRepository.findById(id).get().getText(), map);
        model.addAttribute("text", result);
        return "build";
    }
}
