package com.github.staego.config_builder.services;

import com.github.staego.config_builder.models.Component;
import com.github.staego.config_builder.models.Template;
import com.github.staego.config_builder.repositories.ComponentRepository;
import com.github.staego.config_builder.utils.TemplateEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ComponentService {
    public final ComponentRepository repository;
    private final TemplateService templateService;

    @Autowired
    public ComponentService(ComponentRepository repository, TemplateService templateService) {
        this.repository = repository;
        this.templateService = templateService;
    }

    public Component save(Component component) {
        return repository.save(component);
    }

    public void deleteById(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Шаблон не найден.");
        }
    }

    public List<Component> findAll() {
        return repository.findAll();
    }

    public Component findById(int id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Шаблон не найден."));
    }

    public Component findByName(String name) {
        return repository.findFirstByName(name).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Шаблон не найден."));
    }

    public List<Component> getComponents(int templateId) {
        Template template = templateService.findById(templateId);
        Set<String> names = TemplateEngine.parse(template.getText());
        List<Component> components = repository.findComponentsByName(names);
        for (Component component : components) {
            names.remove(component.getName());
        }
        for (String name : names) {
            Component component = new Component();
            component.setName(name);
            component.setTitle(name);
            components.add(component);
        }
        return components;
    }
}
