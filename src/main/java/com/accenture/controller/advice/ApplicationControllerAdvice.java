package com.accenture.controller.advice;

import com.accenture.exception.IngredientException;
import com.accenture.exception.PizzaException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(IngredientException.class)
    public ResponseEntity<MessageError> handleIngredientException(IngredientException e){
        MessageError me = new MessageError(LocalDateTime.now(), "Erreur validation", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(me);
    }

    @ExceptionHandler(PizzaException.class)
    public ResponseEntity<MessageError> handlePizzaException(PizzaException e){
        MessageError me = new MessageError(LocalDateTime.now(), "Erreur validation", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(me);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MessageError> handleEntityNotFoundException(EntityNotFoundException e){
        MessageError me = new MessageError(LocalDateTime.now(), "Erreur fonctionnelle", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(me);
    }
}
