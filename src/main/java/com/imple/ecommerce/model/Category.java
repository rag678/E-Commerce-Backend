package com.imple.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(max = 50)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;
    private int level;

    public Category() {
    }

    public Category(Long id, String name, Category parentCategory, int level) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
        this.level = level;
    }
}
