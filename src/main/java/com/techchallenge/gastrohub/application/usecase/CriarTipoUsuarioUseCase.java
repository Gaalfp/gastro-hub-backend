package com.techchallenge.gastrohub.application.usecase;

import com.techchallenge.gastrohub.application.dto.TipoUsuarioRequestDTO;
import com.techchallenge.gastrohub.application.dto.TipoUsuarioResponseDTO;
import com.techchallenge.gastrohub.application.gateway.TipoUsuarioGateway;
import com.techchallenge.gastrohub.domain.entity.TipoUsuario;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CriarTipoUsuarioUseCase {
    private final TipoUsuarioGateway gateway;

    public CriarTipoUsuarioUseCase(TipoUsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public TipoUsuarioResponseDTO executar(TipoUsuarioRequestDTO dto) {
        TipoUsuario novo = new TipoUsuario(UUID.randomUUID(), dto.nome());
        return TipoUsuarioResponseDTO.fromDomain(gateway.salvar(novo));
    }
}