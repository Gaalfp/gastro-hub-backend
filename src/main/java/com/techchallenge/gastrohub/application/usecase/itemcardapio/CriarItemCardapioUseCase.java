package com.techchallenge.gastrohub.application.usecase.itemcardapio;

import com.techchallenge.gastrohub.application.dto.ItemCardapioRequestDTO;
import com.techchallenge.gastrohub.application.dto.ItemCardapioResponseDTO;
import com.techchallenge.gastrohub.application.gateway.ItemCardapioGateway;
import com.techchallenge.gastrohub.application.gateway.RestauranteGateway;
import com.techchallenge.gastrohub.domain.entity.ItemCardapio;

public class CriarItemCardapioUseCase {

	private final ItemCardapioGateway itemGateway;
	private final RestauranteGateway restauranteGateway;

	public CriarItemCardapioUseCase(ItemCardapioGateway itemGateway, RestauranteGateway restauranteGateway) {
		this.itemGateway = itemGateway;
		this.restauranteGateway = restauranteGateway;
	}

	public ItemCardapioResponseDTO executar(ItemCardapioRequestDTO dto) {
		restauranteGateway.buscarPorId(dto.restauranteId())
				.orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado com o ID informado."));

		ItemCardapio novo = new ItemCardapio(
				null,
				dto.restauranteId(),
				dto.nome(),
				dto.descricao(),
				dto.preco(),
				dto.apenasLocal(),
				dto.caminhoFoto(),
				true
		);

		ItemCardapio salvo = itemGateway.salvar(novo);

		return ItemCardapioResponseDTO.fromDomain(salvo);
	}
}


