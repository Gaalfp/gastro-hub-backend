package com.techchallenge.gastrohub.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO para solicitar a criação ou atualização de um item do cardápio")
public record ItemCardapioRequestDTO(
        @Schema(description = "ID do restaurante ao qual o item pertence")
        @JsonProperty("restaurante_id")
        UUID restauranteId,

        @Schema(description = "Nome do item", example = "Lasanha Bolonhesa")
        @JsonProperty("nome")
        String nome,

        @Schema(description = "Descrição do item", example = "Lasanha com molho bolonhesa e queijo gratinado")
        @JsonProperty("descricao")
        String descricao,

        @Schema(description = "Preço do item", example = "39.90")
        @JsonProperty("preco")
        BigDecimal preco,

        @Schema(description = "Indica se o item está disponível apenas para consumo no local")
        @JsonProperty("apenas_local")
        boolean apenasLocal,

        @Schema(description = "Caminho onde a foto do prato estaria armazenada")
        @JsonProperty("caminho_foto")
        String caminhoFoto
){
    public ItemCardapioRequestDTO {
        if (restauranteId == null) throw new IllegalArgumentException("restaurante_id é obrigatório");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("nome é obrigatório");
        if (preco == null) throw new IllegalArgumentException("preco é obrigatório");
    }
}

