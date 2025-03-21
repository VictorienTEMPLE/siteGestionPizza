package com.accenture.repository.entity;

import com.accenture.shared.Taille;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class PizzaTailleQuantite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Pizza pizza;
    private Taille taille;
    private Integer quantite;

    public PizzaTailleQuantite(Pizza pizza, Taille taille, Integer quantite) {
        this.pizza = pizza;
        this.taille = taille;
        this.quantite = quantite;
    }
}
