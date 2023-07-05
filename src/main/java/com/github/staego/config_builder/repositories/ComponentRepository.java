package com.github.staego.config_builder.repositories;

import com.github.staego.config_builder.models.Component;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentRepository extends JpaRepository<Component, Integer> {
    List<Component> findByName(String name);
}
