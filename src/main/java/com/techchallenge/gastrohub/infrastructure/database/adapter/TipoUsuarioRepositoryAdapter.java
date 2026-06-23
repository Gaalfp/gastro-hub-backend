package com.techchallenge.gastrohub.infrastructure.database.adapter;

import com.techchallenge.gastrohub.application.gateway.TipoUsuarioGateway;
import com.techchallenge.gastrohub.domain.entity.TipoUsuario;
import com.techchallenge.gastrohub.infrastructure.database.entity.TipoUsuarioEntity;
import com.techchallenge.gastrohub.infrastructure.database.repository.TipoUsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TipoUsuarioRepositoryAdapter implements TipoUsuarioGateway {

    private final TipoUsuarioRepository repository;

    public TipoUsuarioRepositoryAdapter(TipoUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public TipoUsuario salvar(TipoUsuario tipoUsuario) {
        TipoUsuarioEntity entity = new TipoUsuarioEntity();
        entity.setId(tipoUsuario.getId());
        entity.setNome(tipoUsuario.getNome());
        entity.setAtivo(tipoUsuario.isAtivo());

        TipoUsuarioEntity entitySalva = repository.save(entity);

        return new TipoUsuario(entitySalva.getId(), entitySalva.getNome(), entitySalva.isAtivo());
    }

    @Override
    public Optional<TipoUsuario> buscarPorId(UUID id) {
        return repository.findById(id)
                .map(entity -> new TipoUsuario(entity.getId(), entity.getNome(), entity.isAtivo()));
    }

    @Override
    public List<TipoUsuario> buscarTodos() {
        return repository.findAll().stream()
                .map(entity -> new TipoUsuario(entity.getId(), entity.getNome(), entity.isAtivo()))
                .toList();
    }

    @Override
    public void excluir(UUID id) {
        // Podemos manter a assinatura na interface do gateway caso precise excluir fisicamente em algum momento,
        // mas o nosso UseCase de deleção passará a usar o método 'salvar' mudando o estado.
        repository.deleteById(id);
    }
}