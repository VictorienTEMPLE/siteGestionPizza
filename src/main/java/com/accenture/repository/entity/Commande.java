package com.accenture.repository.entity;


import com.accenture.shared.Statut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Client client;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PizzaTailleQuantite> listePizza;
    private Statut statut;
    private Double tarif;


    public Commande(Client client, List<PizzaTailleQuantite> listePizza, Statut statut, Double tarif) {
        this.client = client;
        this.listePizza = listePizza;
        this.statut = statut;
        this.tarif = tarif;
    }
}
