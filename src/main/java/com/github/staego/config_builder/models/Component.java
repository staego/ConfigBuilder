package com.github.staego.config_builder.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "components")
    private final List<Template> templates = new ArrayList<>();

    public Component() {
    }

    public Component(int id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public boolean isCustomized() {
        return title != null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title != null ? title : name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Template> getTemplates() {
        return templates.stream().toList();
    }

    public void setTemplates(Template templates) {
        this.templates.add(templates);
    }
}
