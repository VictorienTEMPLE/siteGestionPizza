package com.accenture.repository.entity;

import com.accenture.shared.Taille;

import java.util.List;

public class Pizza {
    private int id;
    private String nom;
    private Taille taille;
    private List<Ingredient> ingredient;
    private Double tarif;
    private Boolean actif;
}
