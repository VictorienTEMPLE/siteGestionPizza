package com.accenture.repository;

import com.accenture.repository.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientDAO extends JpaRepository<Ingredient, Integer> {
}
