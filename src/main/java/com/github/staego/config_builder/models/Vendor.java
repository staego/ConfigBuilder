package com.github.staego.config_builder.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String title;

    @OneToMany(mappedBy = "vendor")
    private List<Template> templates;

    public boolean isActive(int id) {
        return this.id == id;
    }
}
