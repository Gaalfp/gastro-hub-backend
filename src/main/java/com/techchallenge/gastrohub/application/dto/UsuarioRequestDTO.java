package com.techchallenge.gastrohub.application.dto;

import java.util.UUID;

public record UsuarioRequestDTO(
        String nome,
        String email,
        String login,
        String cpf,
        String senha, 
        String endereco,
        UUID tipoUsuarioId
) {}