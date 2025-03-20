package com.accenture.service.dto;

public record ClientRequestDto(
        String nom,
        String mail,
        Boolean vip
) {
}
