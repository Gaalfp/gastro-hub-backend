package com.techchallenge.gastrohub.application.usecase;

import com.techchallenge.gastrohub.application.gateway.UsuarioGateway;
import com.techchallenge.gastrohub.domain.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InativarUsuarioUseCase {
    private final UsuarioGateway gateway;

    public InativarUsuarioUseCase(UsuarioGateway gateway) { this.gateway = gateway; }

    public void executar(UUID id) {
        Usuario usuario = gateway.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        usuario.setAtivo(false);
        gateway.salvar(usuario);
    }
}