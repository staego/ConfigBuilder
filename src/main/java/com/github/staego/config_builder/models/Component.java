package com.github.staego.config_builder.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column
    private String name;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column
    private String title;

    public boolean isCustomized() {
        return title != null;
    }

    public String getTitle() {
        return title != null ? title : name;
    }
}
