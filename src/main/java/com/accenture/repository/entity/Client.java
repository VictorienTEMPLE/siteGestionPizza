package com.accenture.repository.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String mail;
    private Boolean vip;

    public Client(String nom, String mail, Boolean vip) {
        this.nom = nom;
        this.mail = mail;
        this.vip = vip;
    }
}
