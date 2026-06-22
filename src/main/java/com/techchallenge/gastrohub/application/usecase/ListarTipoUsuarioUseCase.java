package com.techchallenge.gastrohub.application.usecase;

import com.techchallenge.gastrohub.application.dto.TipoUsuarioResponseDTO;
import com.techchallenge.gastrohub.application.gateway.TipoUsuarioGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarTipoUsuarioUseCase {
    private final TipoUsuarioGateway gateway;

    public ListarTipoUsuarioUseCase(TipoUsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public List<TipoUsuarioResponseDTO> executar() {
        return gateway.buscarTodos().stream().map(TipoUsuarioResponseDTO::fromDomain).toList();
    }
}