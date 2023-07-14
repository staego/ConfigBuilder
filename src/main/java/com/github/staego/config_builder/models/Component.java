package com.github.staego.config_builder.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String title;

    public boolean isCustomized() {
        return title != null;
    }

    public String getTitle() {
        return title != null ? title : name;
    }
}
