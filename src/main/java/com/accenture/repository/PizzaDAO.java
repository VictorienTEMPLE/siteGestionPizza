package com.accenture.repository;

import com.accenture.repository.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PizzaDAO extends JpaRepository<Pizza, Integer> {
    Optional<Pizza> findByNomIgnoreCase(String nom);
    List<Pizza> findByIngredientNom(String nomIngredient);
}
