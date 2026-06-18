package com.techchallenge.gastrohub.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techchallenge.gastrohub.domain.entity.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "DTO para resposta de dados de um usuário")
public record UsuarioResponseDTO(
        @Schema(description = "ID do usuário")
        @JsonProperty("id")
        UUID id,

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

        @Schema(description = "Endereço do usuário", example = "Rua das Flores, 456")
        @JsonProperty("endereco")
        String endereco,

        @Schema(description = "ID do tipo de usuário")
        @JsonProperty("tipo_usuario_id")
        UUID tipoUsuarioId,

        @Schema(description = "Indica se o usuário está ativo")
        @JsonProperty("ativo")
        boolean ativo
) {
    public static UsuarioResponseDTO fromDomain(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getCpf(),
                usuario.getEndereco(),
                usuario.getTipoUsuarioId(),
                usuario.isAtivo()
        );
    }
}