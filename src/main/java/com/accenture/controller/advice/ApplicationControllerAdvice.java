package com.accenture.controller.advice;

import com.accenture.exception.ClientException;
import com.accenture.exception.IngredientException;
import com.accenture.exception.PizzaException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(IngredientException.class)
    public ResponseEntity<MessageError> handleIngredientException(IngredientException e){
        log.error("Erreur validation : {}", e.getMessage(), e);
        MessageError me = new MessageError(LocalDateTime.now(), "Erreur validation", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(me);
    }

    @ExceptionHandler(PizzaException.class)
    public ResponseEntity<MessageError> handlePizzaException(PizzaException e){
        log.error("Erreur validation : {}", e.getMessage(), e);
        MessageError me = new MessageError(LocalDateTime.now(), "Erreur validation", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(me);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<MessageError> handleClientException(ClientException e){
        log.error("Erreur validation : {}", e.getMessage(), e);
        MessageError me = new MessageError(LocalDateTime.now(), "Erreur validation", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(me);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MessageError> handleEntityNotFoundException(EntityNotFoundException e){
        log.error("Erreur fonctionnelle : {}", e.getMessage(), e);
        MessageError me = new MessageError(LocalDateTime.now(), "Erreur fonctionnelle", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(me);
    }
}
