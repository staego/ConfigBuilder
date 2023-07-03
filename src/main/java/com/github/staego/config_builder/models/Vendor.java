package com.github.staego.config_builder.models;

import jakarta.persistence.*;

import java.util.List;

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

    public Vendor() {

    }

    public Vendor(int id, String name, String title, List<Template> templates) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.templates = templates;
    }

    public boolean isActive(int id) {
        return this.id == id;
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
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }
}
