package com.techchallenge.gastrohub.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techchallenge.gastrohub.domain.entity.Restaurante;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "DTO para resposta de dados de um restaurante")
public record RestauranteResponseDTO(
        @Schema(description = "ID do restaurante")
        @JsonProperty("id")
        UUID id,

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
    public static RestauranteResponseDTO fromDomain(Restaurante restaurante) {
        return new RestauranteResponseDTO(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getDonoId()
        );
    }
}