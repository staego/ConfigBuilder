package com.github.staego.config_builder.repositories;

import com.github.staego.config_builder.models.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ComponentRepository extends JpaRepository<Component, Integer> {
    Optional<Component> findFirstByName(String name);

    @Query(value = "SELECT c.* FROM component c WHERE c.name IN :names", nativeQuery = true)
    List<Component> findComponentsByName(@Param("names") Set<String> names);
}
