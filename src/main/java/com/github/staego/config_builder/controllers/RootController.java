package com.github.staego.config_builder.controllers;

import com.github.staego.config_builder.models.Vendor;
import com.github.staego.config_builder.services.ComponentService;
import com.github.staego.config_builder.services.TemplateService;
import com.github.staego.config_builder.services.VendorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RootController {
    private final TemplateService templateService;
    private final VendorService vendorService;
    private final ComponentService componentService;

    @Autowired
    public RootController(TemplateService templateService, VendorService vendorService, ComponentService componentService) {
        this.templateService = templateService;
        this.vendorService = vendorService;
        this.componentService = componentService;
    }

    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("vendors", vendorService.findAll());
        model.addAttribute("path", "/");
        return "category";
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("templates", templateService.findAll());
        model.addAttribute("filter_title", "Все");
        return "index";
    }

    @GetMapping("/filter/{filter}")
    public String indexWi(@PathVariable String filter, Model model) {
        Vendor vendor = vendorService.findByName(filter);
        model.addAttribute("templates", vendor.getTemplates());
        model.addAttribute("filter_title", vendor.getTitle());
        return "index";
    }

    @GetMapping("/builder/{id}")
    public String builder(@PathVariable("id") int id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("components", componentService.getComponents(id));
        return "builder";
    }

    @PostMapping("/build/{id}")
    public String build(@PathVariable("id") int id, HttpServletRequest request, Model model) {
        model.addAttribute("text", templateService.renderTemplate(id, request));
        return "build";
    }
}
