package com.techchallenge.gastrohub.application.dto;

import com.techchallenge.gastrohub.domain.entity.Usuario;
import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        String login,
        String cpf,
        String endereco,
        UUID tipoUsuarioId,
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