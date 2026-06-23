package com.techchallenge.gastrohub.application.usecase;

import com.techchallenge.gastrohub.application.dto.ItemCardapioRequestDTO;
import com.techchallenge.gastrohub.application.dto.ItemCardapioResponseDTO;
import com.techchallenge.gastrohub.application.gateway.ItemCardapioGateway;
import com.techchallenge.gastrohub.domain.entity.ItemCardapio;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AtualizarItemCardapioUseCase {

    private final ItemCardapioGateway itemGateway;

    public AtualizarItemCardapioUseCase(ItemCardapioGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public ItemCardapioResponseDTO executar(UUID id, ItemCardapioRequestDTO dto) {
        ItemCardapio existente = itemGateway.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Item do cardápio não encontrado."));

        existente.setNome(dto.nome());
        existente.setDescricao(dto.descricao());
        existente.setPreco(dto.preco());
        existente.setApenasLocal(dto.apenasLocal());
        existente.setCaminhoFoto(dto.caminhoFoto());

        ItemCardapio salvo = itemGateway.salvar(existente);

        return ItemCardapioResponseDTO.fromDomain(salvo);
    }
}

