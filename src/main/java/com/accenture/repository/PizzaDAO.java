package com.accenture.repository;

import com.accenture.repository.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaDAO extends JpaRepository<Pizza, Integer> {
}
