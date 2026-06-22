package com.techchallenge.gastrohub.application.usecase;

import com.techchallenge.gastrohub.application.gateway.TipoUsuarioGateway;
import com.techchallenge.gastrohub.domain.entity.TipoUsuario;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InativarTipoUsuarioUseCase {
    private final TipoUsuarioGateway gateway;

    public InativarTipoUsuarioUseCase(TipoUsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public void executar(UUID id) {
        TipoUsuario tipoUsuario = gateway.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de usuário não encontrado."));

        tipoUsuario.setAtivo(false);

        gateway.salvar(tipoUsuario);
    }
}