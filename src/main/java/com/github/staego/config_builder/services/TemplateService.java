package com.github.staego.config_builder.services;

import com.github.staego.config_builder.models.Template;
import com.github.staego.config_builder.repositories.TemplateRepository;
import com.github.staego.config_builder.utils.TemplateEngine;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TemplateService {
    private final TemplateRepository repository;

    @Autowired
    public TemplateService(TemplateRepository repository) {
        this.repository = repository;
    }

    public String renderTemplate(int id, HttpServletRequest request) {
        Map<String, String> components = request.getParameterMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]) );
        Template template = findById(id);
        return TemplateEngine.render(template.getText(), components);
    }

    public Template save(Template template) {
        // TODO
        return repository.save(template);
    }

    public void deleteById(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Шаблон не найден.");
        }
    }

    public List<Template> findAll() {
        return repository.findAll();
    }

    public Template findById(int id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Шаблон не найден."));
    }
}
