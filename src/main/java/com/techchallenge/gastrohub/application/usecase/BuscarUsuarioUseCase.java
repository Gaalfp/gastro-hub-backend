package com.techchallenge.gastrohub.application.usecase;

import com.techchallenge.gastrohub.application.dto.UsuarioResponseDTO;
import com.techchallenge.gastrohub.application.gateway.UsuarioGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BuscarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public BuscarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public List<UsuarioResponseDTO> buscarTodos() {
        return usuarioGateway.buscarTodos().stream()
                .map(UsuarioResponseDTO::fromDomain)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(UUID id) {
        return usuarioGateway.buscarPorId(id)
                .map(UsuarioResponseDTO::fromDomain)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID informado."));
    }
}