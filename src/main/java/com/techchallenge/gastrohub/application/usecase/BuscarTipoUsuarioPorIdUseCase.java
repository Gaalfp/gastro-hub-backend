package com.techchallenge.gastrohub.application.usecase;

import com.techchallenge.gastrohub.application.dto.TipoUsuarioResponseDTO;
import com.techchallenge.gastrohub.application.gateway.TipoUsuarioGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuscarTipoUsuarioPorIdUseCase {
    private final TipoUsuarioGateway gateway;

    public BuscarTipoUsuarioPorIdUseCase(TipoUsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public TipoUsuarioResponseDTO executar(UUID id) {
        return gateway.buscarPorId(id).map(TipoUsuarioResponseDTO::fromDomain).orElseThrow();
    }
}
