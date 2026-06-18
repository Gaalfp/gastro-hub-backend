package com.techchallenge.gastrohub.application.gateway;

import com.techchallenge.gastrohub.domain.entity.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioGateway {
    Usuario salvar(Usuario usuario);
    List<Usuario> buscarTodos();
    Optional<Usuario> buscarPorId(UUID id);
}