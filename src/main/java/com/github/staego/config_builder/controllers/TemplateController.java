package com.github.staego.config_builder.controllers;

import com.github.staego.config_builder.models.Template;
import com.github.staego.config_builder.models.Vendor;
import com.github.staego.config_builder.repositories.TemplateRepository;
import com.github.staego.config_builder.repositories.VendorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/template")
public class TemplateController {
    private static final String REDIRECT =  "redirect:/template";
    private static final String TEMPLATE_PATH = "template/";
    private final VendorRepository vendorRepository;
    private final TemplateRepository templateRepository;

    public TemplateController(VendorRepository vendorRepository, TemplateRepository templateRepository) {
        this.vendorRepository = vendorRepository;
        this.templateRepository = templateRepository;
    }

    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("vendors", vendorRepository.findAll());
        model.addAttribute("path", "template");
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
        return TEMPLATE_PATH + "index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("vendors", vendorRepository.findAll());
        model.addAttribute("template", new Template());
        return TEMPLATE_PATH + "add";
    }

    @PostMapping
    public String add(@ModelAttribute("template") Template template) {
        templateRepository.save(template);
        return REDIRECT;
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Template> template = templateRepository.findById(id);
        if (template.isEmpty()) {
            return REDIRECT;
        }
        model.addAttribute("template", template.get());
        model.addAttribute("vendors", vendorRepository.findAll());
        model.addAttribute("active_id", template.get().getVendor().getId());
        return TEMPLATE_PATH + "edit";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("template") Template template) {
        templateRepository.save(template);
        return REDIRECT;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        Optional<Template> template = templateRepository.findById(id);
        if (template.isEmpty()) {
            return REDIRECT;
        }
        model.addAttribute("template", template.get());
        return TEMPLATE_PATH + "delete";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        templateRepository.deleteById(id);
        return REDIRECT;
    }
}
