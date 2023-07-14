package com.github.staego.config_builder.repositories;

import com.github.staego.config_builder.models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    Optional<Vendor> findFirstByName(String name);
}
