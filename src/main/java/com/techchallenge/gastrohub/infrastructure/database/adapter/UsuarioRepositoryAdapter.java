package com.techchallenge.gastrohub.infrastructure.database.adapter;

import com.techchallenge.gastrohub.application.gateway.UsuarioGateway;
import com.techchallenge.gastrohub.domain.entity.Usuario;
import com.techchallenge.gastrohub.infrastructure.database.entity.TipoUsuarioEntity;
import com.techchallenge.gastrohub.infrastructure.database.entity.UsuarioEntity;
import com.techchallenge.gastrohub.infrastructure.database.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UsuarioRepositoryAdapter implements UsuarioGateway {

    private final UsuarioRepository repository;

    public UsuarioRepositoryAdapter(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity entity = toEntity(usuario);
        UsuarioEntity savedEntity = repository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    private UsuarioEntity toEntity(Usuario domain) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setEmail(domain.getEmail());
        entity.setLogin(domain.getLogin());
        entity.setCpf(domain.getCpf());
        entity.setSenhaHash(domain.getSenhaHash());
        entity.setEndereco(domain.getEndereco());
        entity.setAtivo(domain.isAtivo());

        if (domain.getTipoUsuarioId() != null) {
            TipoUsuarioEntity tipo = new TipoUsuarioEntity();
            tipo.setId(domain.getTipoUsuarioId());
            entity.setTipoUsuario(tipo);
        }
        return entity;
    }

    private Usuario toDomain(UsuarioEntity entity) {
        return new Usuario(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getCpf(),
                entity.getSenhaHash(),
                entity.getEndereco(),
                entity.getTipoUsuario().getId(),
                entity.isAtivo()
        );
    }
}