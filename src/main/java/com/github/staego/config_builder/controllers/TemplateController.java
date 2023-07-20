package com.github.staego.config_builder.controllers;

import com.github.staego.config_builder.models.Template;
import com.github.staego.config_builder.models.Vendor;
import com.github.staego.config_builder.services.TemplateService;
import com.github.staego.config_builder.services.VendorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/template")
public class TemplateController {
    private static final String REDIRECT =  "redirect:/template";
    private static final String TEMPLATE_PATH = "template/";
    private final VendorService vendorService;
    private final TemplateService templateService;

    public TemplateController(VendorService vendorService, TemplateService templateService) {
        this.vendorService = vendorService;
        this.templateService = templateService;
    }

    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("vendors", vendorService.findAll());
        model.addAttribute("path", "/template");
        return "category";
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("templates", templateService.findAll());
        model.addAttribute("filter_title", "Все");
        return TEMPLATE_PATH + "index";
    }

    @GetMapping("/filter/{filter}")
    public String indexWi(@PathVariable String filter, Model model) {
        Vendor vendor = vendorService.findByName(filter);
        model.addAttribute("templates", vendor.getTemplates());
        model.addAttribute("filter_title", vendor.getTitle());
        return TEMPLATE_PATH + "index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("vendors", vendorService.findAll());
        model.addAttribute("template", new Template());
        return TEMPLATE_PATH + "add";
    }

    @PostMapping
    public String add(@ModelAttribute("template") @Valid Template template, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return TEMPLATE_PATH + "add";
        }
        templateService.save(template);
        return REDIRECT;
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Template template = templateService.findById(id);
        model.addAttribute("vendors", vendorService.findAll());
        model.addAttribute("template", template);
        model.addAttribute("active_id", template.getVendor().getId());
        return TEMPLATE_PATH + "edit";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("template") @Valid Template template, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return TEMPLATE_PATH + "edit";
        }
        templateService.save(template);
        return REDIRECT;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        model.addAttribute("template", templateService.findById(id));
        return TEMPLATE_PATH + "delete";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        templateService.deleteById(id);
        return REDIRECT;
    }
}
