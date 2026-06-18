package com.techchallenge.gastrohub.application.dto;

import java.util.UUID;

public record RestauranteRequestDTO(
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioFuncionamento,
        UUID donoId
) {
    public RestauranteRequestDTO {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do restaurante é obrigatório.");
        }
        if (donoId == null) {
            throw new IllegalArgumentException("O restaurante precisa ter um dono associado.");
        }
    }
}