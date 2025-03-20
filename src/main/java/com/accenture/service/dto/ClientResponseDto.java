package com.accenture.service.dto;

public record ClientResponseDto (
        int id,
        String nom,
        String mail,
        Boolean vip
){
}
