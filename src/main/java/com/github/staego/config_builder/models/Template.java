package com.github.staego.config_builder.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "config_template")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column(name = "config_template")
    private String text;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private Vendor vendor;
}
