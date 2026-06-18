package com.techchallenge.gastrohub.application.usecase;

import com.techchallenge.gastrohub.application.dto.UsuarioRequestDTO;
import com.techchallenge.gastrohub.application.dto.UsuarioResponseDTO;
import com.techchallenge.gastrohub.application.gateway.UsuarioGateway;
import com.techchallenge.gastrohub.domain.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AtualizarUsuarioUseCase {
    private final UsuarioGateway gateway;

    public AtualizarUsuarioUseCase(UsuarioGateway gateway) { this.gateway = gateway; }

    public UsuarioResponseDTO executar(UUID id, UsuarioRequestDTO dto) {
        Usuario usuario = gateway.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setEndereco(dto.endereco());

        return UsuarioResponseDTO.fromDomain(gateway.salvar(usuario));
    }
}