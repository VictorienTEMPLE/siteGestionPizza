package com.accenture.repository;

import com.accenture.repository.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeDAO extends JpaRepository<Commande, Integer> {
}
