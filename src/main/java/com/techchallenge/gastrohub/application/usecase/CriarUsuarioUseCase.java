package com.techchallenge.gastrohub.application.usecase;

import com.techchallenge.gastrohub.application.dto.UsuarioRequestDTO;
import com.techchallenge.gastrohub.application.dto.UsuarioResponseDTO;
import com.techchallenge.gastrohub.application.gateway.UsuarioGateway;
import com.techchallenge.gastrohub.domain.entity.Usuario;
import org.springframework.stereotype.Service;

@Service
public class CriarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public CriarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public UsuarioResponseDTO executar(UsuarioRequestDTO dto) {
        String senhaHasheada = dto.senha() + "_HASHED";

        Usuario novoUsuario = new Usuario(
                null,
                dto.nome(),
                dto.email(),
                dto.login(),
                dto.cpf(),
                senhaHasheada,
                dto.endereco(),
                dto.tipoUsuarioId(),
                true
        );

        Usuario usuarioSalvo = usuarioGateway.salvar(novoUsuario);
        return UsuarioResponseDTO.fromDomain(usuarioSalvo);
    }
}