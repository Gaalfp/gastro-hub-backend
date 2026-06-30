package com.techchallenge.gastrohub.application.usecase.usuario;

import com.techchallenge.gastrohub.application.dto.UsuarioRequestDTO;
import com.techchallenge.gastrohub.application.dto.UsuarioResponseDTO;
import com.techchallenge.gastrohub.application.gateway.UsuarioGateway;
import com.techchallenge.gastrohub.domain.entity.Usuario;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class CriarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public CriarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Transactional
    public UsuarioResponseDTO executar(UsuarioRequestDTO request) {
        String cpfLimpo = request.cpf() != null ? request.cpf().replaceAll("[^0-9]", "") : "";

        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("CPF inválido: deve conter 11 dígitos numéricos.");
        }

        Usuario novoUsuario = new Usuario(
                UUID.randomUUID(),
                request.nome(),
                request.email(),
                request.login(),
                cpfLimpo,
                request.senha(),
                request.endereco(),
                request.tipoUsuarioId(),
                true
        );

        Usuario usuarioSalvo = usuarioGateway.salvar(novoUsuario);

        return UsuarioResponseDTO.fromDomain(usuarioSalvo);
    }
}