package com.techchallenge.gastrohub.application.dto;

import com.techchallenge.gastrohub.domain.entity.Restaurante;
import java.util.UUID;

public record RestauranteResponseDTO(
        UUID id,
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioFuncionamento,
        UUID donoId
) {
    public static RestauranteResponseDTO fromDomain(Restaurante restaurante) {
        return new RestauranteResponseDTO(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getDonoId()
        );
    }
}