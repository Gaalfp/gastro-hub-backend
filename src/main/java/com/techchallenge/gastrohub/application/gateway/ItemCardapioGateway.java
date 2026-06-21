package com.techchallenge.gastrohub.application.gateway;

import com.techchallenge.gastrohub.domain.entity.ItemCardapio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemCardapioGateway {
    ItemCardapio salvar(ItemCardapio item);
    Optional<ItemCardapio> buscarPorId(UUID id);
    List<ItemCardapio> buscarPorRestauranteId(UUID restauranteId);
}

