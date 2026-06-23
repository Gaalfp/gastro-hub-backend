package com.techchallenge.gastrohub.infrastructure.persistence.adapter;

import com.techchallenge.gastrohub.application.gateway.RestauranteGateway;
import com.techchallenge.gastrohub.domain.entity.Restaurante;
import com.techchallenge.gastrohub.infrastructure.persistence.entity.RestauranteEntity;
import com.techchallenge.gastrohub.infrastructure.persistence.entity.UsuarioEntity;
import com.techchallenge.gastrohub.infrastructure.persistence.repository.RestauranteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RestauranteRepositoryAdapter implements RestauranteGateway {

    private final RestauranteRepository repository;

    public RestauranteRepositoryAdapter(RestauranteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        RestauranteEntity entity = toEntity(restaurante);

        RestauranteEntity savedEntity = repository.save(entity);

        return toDomain(savedEntity);
    }

    @Override
    public Optional<Restaurante> buscarPorId(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Page<Restaurante> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toDomain);
    }

    private RestauranteEntity toEntity(Restaurante domain) {
        RestauranteEntity entity = new RestauranteEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setEndereco(domain.getEndereco());
        entity.setTipoCozinha(domain.getTipoCozinha());
        entity.setHorarioFuncionamento(domain.getHorarioFuncionamento());
        entity.setAtivo(domain.isAtivo());

        if (domain.getDonoId() != null) {
            UsuarioEntity donoEntity = new UsuarioEntity();
            donoEntity.setId(domain.getDonoId());
            entity.setDono(donoEntity);
        }

        return entity;
    }

    private Restaurante toDomain(RestauranteEntity entity) {
        return new Restaurante(
                entity.getId(),
                entity.getNome(),
                entity.getEndereco(),
                entity.getTipoCozinha(),
                entity.getHorarioFuncionamento(),
                entity.getDono().getId(),
                entity.isAtivo()
        );
    }
}