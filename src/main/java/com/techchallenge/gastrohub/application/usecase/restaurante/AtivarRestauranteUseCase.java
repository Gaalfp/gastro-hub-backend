package com.techchallenge.gastrohub.application.usecase.restaurante;

import com.techchallenge.gastrohub.application.gateway.RestauranteGateway;
import com.techchallenge.gastrohub.domain.entity.Restaurante;
import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class AtivarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    public AtivarRestauranteUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public void executar(UUID idRestaurante) {
        Restaurante restaurante = restauranteGateway.buscarPorId(idRestaurante)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado com o ID informado."));

        restaurante.ativar();
        restauranteGateway.salvar(restaurante);
    }
}
