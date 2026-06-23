package com.techchallenge.gastrohub.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO de resposta para Item do Cardápio")
public record ItemCardapioResponseDTO(
		@JsonProperty("id") UUID id,
		@JsonProperty("restaurante_id") UUID restauranteId,
		@JsonProperty("nome") String nome,
		@JsonProperty("descricao") String descricao,
		@JsonProperty("preco") BigDecimal preco,
		@JsonProperty("apenas_local") boolean apenasLocal,
		@JsonProperty("caminho_foto") String caminhoFoto,
		@JsonProperty("ativo") boolean ativo
) {
	public static ItemCardapioResponseDTO fromDomain(com.techchallenge.gastrohub.domain.entity.ItemCardapio domain) {
		return new ItemCardapioResponseDTO(
				domain.getId(),
				domain.getRestauranteId(),
				domain.getNome(),
				domain.getDescricao(),
				domain.getPreco(),
				domain.isApenasLocal(),
				domain.getCaminhoFoto(),
				domain.isAtivo()
		);
	}
}


