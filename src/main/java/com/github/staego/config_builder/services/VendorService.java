package com.github.staego.config_builder.services;

import com.github.staego.config_builder.models.Vendor;
import com.github.staego.config_builder.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class VendorService {
    private final VendorRepository repository;

    @Autowired
    public VendorService(VendorRepository repository) {
        this.repository = repository;
    }

    public Vendor save(Vendor vendor) {
        vendor.setName(vendor.getName().toLowerCase());
        return repository.save(vendor);
    }

    public void deleteById(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Вендор не найден.");
        }
    }

    public List<Vendor> findAll() {
        return repository.findAll();
    }

    public Vendor findById(int id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Шаблон не найден."));
    }

    public Vendor findByName(String name) {
        return repository.findFirstByName(name).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Шаблон не найден."));
    }
}
