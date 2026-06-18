package com.techchallenge.gastrohub.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO para solicitar a criação ou atualização de um usuário")
public record UsuarioRequestDTO(
        @Schema(description = "Nome do usuário", example = "João da Silva")
        @JsonProperty("nome")
        String nome,

        @Schema(description = "E-mail do usuário", example = "joao.silva@example.com")
        @JsonProperty("email")
        String email,

        @Schema(description = "Login do usuário", example = "joao.silva")
        @JsonProperty("login")
        String login,

        @Schema(description = "CPF do usuário", example = "123.456.789-00")
        @JsonProperty("cpf")
        String cpf,

        @Schema(description = "Senha do usuário", example = "senha123")
        @JsonProperty("senha")
        String senha,

        @Schema(description = "Endereço do usuário", example = "Rua das Flores, 456")
        @JsonProperty("endereco")
        String endereco,

        @Schema(description = "ID do tipo de usuário")
        @JsonProperty("tipo_usuario_id")
        UUID tipoUsuarioId
) {}