package com.techchallenge.gastrohub.application.dto;

import com.techchallenge.gastrohub.domain.entity.TipoUsuario;

import java.util.UUID;

public record TipoUsuarioResponseDTO(UUID id, String nome) {

    public static TipoUsuarioResponseDTO fromDomain(TipoUsuario tipoUsuario) {
        return new TipoUsuarioResponseDTO(tipoUsuario.getId(), tipoUsuario.getNome());
    }
}