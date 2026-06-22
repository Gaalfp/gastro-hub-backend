package com.techchallenge.gastrohub.application.gateway;

import com.techchallenge.gastrohub.domain.entity.TipoUsuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TipoUsuarioGateway {
    TipoUsuario salvar(TipoUsuario tipoUsuario);
    Optional<TipoUsuario> buscarPorId(UUID id);
    List<TipoUsuario> buscarTodos();
    void excluir(UUID id);
}