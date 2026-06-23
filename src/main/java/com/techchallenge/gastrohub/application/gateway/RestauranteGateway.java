package com.techchallenge.gastrohub.application.gateway;

import com.techchallenge.gastrohub.domain.entity.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface RestauranteGateway {
    Restaurante salvar(Restaurante restaurante);
    Optional<Restaurante> buscarPorId(UUID id);
    Page<Restaurante> buscarTodos(Pageable pageable);
}