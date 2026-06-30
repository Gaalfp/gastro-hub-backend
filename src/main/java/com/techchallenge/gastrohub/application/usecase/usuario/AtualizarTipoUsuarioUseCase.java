package com.techchallenge.gastrohub.application.usecase.usuario;

import com.techchallenge.gastrohub.application.dto.TipoUsuarioRequestDTO;
import com.techchallenge.gastrohub.application.dto.TipoUsuarioResponseDTO;
import com.techchallenge.gastrohub.application.gateway.TipoUsuarioGateway;
import com.techchallenge.gastrohub.domain.entity.TipoUsuario;

import java.util.UUID;

public class AtualizarTipoUsuarioUseCase {
    private final TipoUsuarioGateway gateway;

    public AtualizarTipoUsuarioUseCase(TipoUsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public TipoUsuarioResponseDTO executar(UUID id, TipoUsuarioRequestDTO dto) {
        TipoUsuario tipo = gateway.buscarPorId(id).orElseThrow();
        tipo.setNome(dto.nome());
        return TipoUsuarioResponseDTO.fromDomain(gateway.salvar(tipo));
    }
}