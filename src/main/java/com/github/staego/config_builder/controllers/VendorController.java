package com.github.staego.config_builder.controllers;

import com.github.staego.config_builder.models.Vendor;
import com.github.staego.config_builder.services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/template/vendor")
public class VendorController {
    private static final String REDIRECT =  "redirect:/template/vendor";
    private static final String TEMPLATE_PATH = "/template/vendor/";
    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("vendors", vendorService.findAll());
        return TEMPLATE_PATH + "index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("vendor", new Vendor());
        return TEMPLATE_PATH + "add";
    }

    @PostMapping
    public String add(@ModelAttribute("vendor") Vendor vendor) {
        vendorService.save(vendor);
        return REDIRECT;
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("vendor", vendorService.findById(id));
        return TEMPLATE_PATH + "edit";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("vendor") Vendor vendor) {
        vendorService.save(vendor);
        return REDIRECT;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        model.addAttribute("vendor", vendorService.findById(id));
        return TEMPLATE_PATH + "delete";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        vendorService.deleteById(id);
        return REDIRECT;
    }
}
