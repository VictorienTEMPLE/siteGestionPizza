package com.accenture.repository.entity;

import com.accenture.shared.Taille;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    @ElementCollection
    @CollectionTable(name = "pizza_tarif", joinColumns = @JoinColumn(name = "pizza_id"))
    @MapKeyEnumerated(EnumType.STRING) // Stocke la clé de l'EnumMap sous forme de chaîne
    @MapKeyColumn(name = "taille")
    @Column(name = "tarif") // Valeur stockée
    private Map<Taille, Double> tarif;
    @ManyToMany
    @JoinTable(name = "pizza_ingredient",
    joinColumns = {@JoinColumn(name ="pizza_id")},
    inverseJoinColumns = {@JoinColumn(name = "ingredient_id")})
    private List<Ingredient> ingredient;
    private Boolean actif;

    public Pizza(String nom, Map<Taille, Double> tarif, List<Ingredient> ingredient, Boolean actif) {
        this.nom = nom;
        this.tarif = tarif;
        this.ingredient = ingredient;
        this.actif = actif;
    }


}
