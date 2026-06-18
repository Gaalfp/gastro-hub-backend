package com.techchallenge.gastrohub.application.gateway;

import com.techchallenge.gastrohub.domain.entity.Restaurante;
import java.util.Optional;
import java.util.UUID;

public interface RestauranteGateway {
    Restaurante salvar(Restaurante restaurante);
    Optional<Restaurante> buscarPorId(UUID id);
}