package com.github.staego.config_builder.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ttemplate")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column(name = "ctemplate")
    private String text;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private Vendor vendor;

    @ManyToMany
    @JoinTable(
            name = "template_component",
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = " component_id")
    )
    private final List<Component> components = new ArrayList<>();

    public Template() {
    }

    public Template(int id, String title, String template, Vendor vendor) {
        this.id = id;
        this.title = title;
        this.text = template;
        this.vendor = vendor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public List<Component> getComponents() {
        return components.stream().toList();
    }

    public void setComponents(Component components) {
        this.components.add(components);
    }
}
