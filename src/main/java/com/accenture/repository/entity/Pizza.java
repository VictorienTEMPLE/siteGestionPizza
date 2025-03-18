package com.accenture.repository.entity;

import com.accenture.shared.Taille;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private HashMap<Taille, Double>tarif;
    @ManyToMany
    private List<Ingredient> ingredient;
    private Boolean actif;

    public Pizza(String nom, HashMap<Taille, Double> tarif, List<Ingredient> ingredient, Boolean actif) {
        this.nom = nom;
        this.tarif = tarif;
        this.ingredient = ingredient;
        this.actif = actif;
    }
}
