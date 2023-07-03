package com.github.staego.config_builder.controllers;

import com.github.staego.config_builder.models.Vendor;
import com.github.staego.config_builder.repositories.TemplateRepository;
import com.github.staego.config_builder.repositories.VendorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/vendor")
public class VendorController {
    private static final String REDIRECT =  "redirect:/vendor";
    private static final String TEMPLATE_PATH = "vendor/";
    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("vendors", vendorRepository.findAll());
        return TEMPLATE_PATH + "index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("vendor", new Vendor());
        return TEMPLATE_PATH + "add";
    }

    @PostMapping
    public String add(@ModelAttribute("vendor") Vendor vendor) {
        vendorRepository.save(vendor);
        return REDIRECT;
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        if (vendor.isEmpty()) {
            return REDIRECT;
        }
        model.addAttribute("vendor", vendor.get());
        return TEMPLATE_PATH + "edit";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("vendor") Vendor vendor) {
        System.out.println(vendor.getId());
        vendorRepository.save(vendor);
        return REDIRECT;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        if (vendor.isEmpty()) {
            return REDIRECT;
        }
        model.addAttribute("vendor", vendor.get());
        return TEMPLATE_PATH + "delete";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        vendorRepository.deleteById(id);
        return REDIRECT;
    }
}
