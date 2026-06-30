package com.techchallenge.gastrohub.application.usecase.restaurante;

import com.techchallenge.gastrohub.application.dto.RestauranteResponseDTO;
import com.techchallenge.gastrohub.application.gateway.RestauranteGateway;
import com.techchallenge.gastrohub.domain.entity.Restaurante;
import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class BuscarRestaurantePorIdUseCase {

    private final RestauranteGateway restauranteGateway;

    public BuscarRestaurantePorIdUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public RestauranteResponseDTO executar(UUID idRestaurante) {
        Restaurante restaurante = restauranteGateway.buscarPorId(idRestaurante)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado com o ID informado."));

        return RestauranteResponseDTO.fromDomain(restaurante);
    }
}
