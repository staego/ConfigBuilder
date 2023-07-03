package com.github.staego.config_builder.repositories;

import com.github.staego.config_builder.models.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer> {
}
