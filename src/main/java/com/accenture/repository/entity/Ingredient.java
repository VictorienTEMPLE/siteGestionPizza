package com.accenture.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private Integer quantiteEnStock;
    private Boolean enStock;

    public Ingredient(String nom, Integer quantiteEnStock, Boolean enStock) {
        this.nom = nom;
        this.quantiteEnStock = quantiteEnStock;
        this.enStock = enStock;
    }
}
