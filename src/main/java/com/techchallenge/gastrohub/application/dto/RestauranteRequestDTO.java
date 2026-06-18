package com.techchallenge.gastrohub.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO para solicitar a criação ou atualização de um restaurante")
public record RestauranteRequestDTO(
        @Schema(description = "Nome do restaurante", example = "Cantina Italiana")
        @JsonProperty("nome")
        String nome,

        @Schema(description = "Endereço do restaurante", example = "Rua das Pizzas, 123")
        @JsonProperty("endereco")
        String endereco,

        @Schema(description = "Tipo de cozinha do restaurante", example = "Italiana")
        @JsonProperty("tipo_cozinha")
        String tipoCozinha,

        @Schema(description = "Horário de funcionamento do restaurante", example = "18:00 - 23:00")
        @JsonProperty("horario_funcionamento")
        String horarioFuncionamento,

        @Schema(description = "ID do proprietário do restaurante")
        @JsonProperty("dono_id")
        UUID donoId
) {
    public RestauranteRequestDTO {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do restaurante é obrigatório.");
        }
        if (donoId == null) {
            throw new IllegalArgumentException("O restaurante precisa ter um dono associado.");
        }
    }
}