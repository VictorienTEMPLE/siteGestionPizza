package com.accenture.repository.entity;

import com.accenture.shared.Taille;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    @ElementCollection
    @CollectionTable(name = "pizza_tarif", joinColumns = @JoinColumn(name = "pizza_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "tarif")
    private Map<Taille, Double> tarif = new EnumMap<>(Taille.class);
    @ManyToMany
    @JoinTable(name = "pizza_ingredient",
    joinColumns = {@JoinColumn(name="id_pizza")},
    inverseJoinColumns = {@JoinColumn(name="id_ingredient")})
    private List<Ingredient> ingredient;
    private Boolean actif = true;

    public Pizza(String nom, HashMap<Taille, Double> tarif, List<Ingredient> ingredient, Boolean actif) {
        this.nom = nom;
        this.tarif = tarif;
        this.ingredient = ingredient;
        this.actif = actif;
    }
}
