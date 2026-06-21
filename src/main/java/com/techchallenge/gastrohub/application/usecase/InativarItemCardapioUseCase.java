package com.techchallenge.gastrohub.application.usecase;

import com.techchallenge.gastrohub.application.gateway.ItemCardapioGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InativarItemCardapioUseCase {

    private final ItemCardapioGateway itemGateway;

    public InativarItemCardapioUseCase(ItemCardapioGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public void executar(UUID id) {
        var existente = itemGateway.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Item do cardápio não encontrado."));

        existente.desativar();
        itemGateway.salvar(existente);
    }
}

