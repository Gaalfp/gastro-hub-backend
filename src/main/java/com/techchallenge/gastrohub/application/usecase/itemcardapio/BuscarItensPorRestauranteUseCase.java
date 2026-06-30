package com.techchallenge.gastrohub.application.usecase.itemcardapio;

import com.techchallenge.gastrohub.application.dto.ItemCardapioResponseDTO;
import com.techchallenge.gastrohub.application.gateway.ItemCardapioGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BuscarItensPorRestauranteUseCase {

    private final ItemCardapioGateway itemGateway;

    public BuscarItensPorRestauranteUseCase(ItemCardapioGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public List<ItemCardapioResponseDTO> executar(UUID restauranteId) {
        return itemGateway.buscarPorRestauranteId(restauranteId)
                .stream()
                .map(ItemCardapioResponseDTO::fromDomain)
                .collect(Collectors.toList());
    }
}

