package com.accenture.controller;


import com.accenture.service.PizzaService;
import com.accenture.service.dto.PizzaResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {
     private final PizzaService pizzaService;


    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @DeleteMapping("/{id}")
    ResponseEntity<PizzaResponseDto> supprimer(@PathVariable int id){
        PizzaResponseDto responseDto = pizzaService.supprimer(id);
        return ResponseEntity.ok(responseDto);
    }


}
