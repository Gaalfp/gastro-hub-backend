package com.techchallenge.gastrohub.infrastructure.database.adapter;

import com.techchallenge.gastrohub.application.gateway.ItemCardapioGateway;
import com.techchallenge.gastrohub.domain.entity.ItemCardapio;
import com.techchallenge.gastrohub.infrastructure.database.entity.ItemCardapioEntity;
import com.techchallenge.gastrohub.infrastructure.database.entity.RestauranteEntity;
import com.techchallenge.gastrohub.infrastructure.database.repository.ItemCardapioRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ItemCardapioRepositoryAdapter implements ItemCardapioGateway {

    private final ItemCardapioRepository repository;

    public ItemCardapioRepositoryAdapter(ItemCardapioRepository repository) {
        this.repository = repository;
    }

    @Override
    public ItemCardapio salvar(ItemCardapio item) {
        ItemCardapioEntity entity = toEntity(item);

        ItemCardapioEntity saved = repository.save(entity);

        return toDomain(saved);
    }

    @Override
    public Optional<ItemCardapio> buscarPorId(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<ItemCardapio> buscarPorRestauranteId(UUID restauranteId) {
        return repository.findByRestauranteIdAndAtivoTrue(restauranteId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private ItemCardapioEntity toEntity(ItemCardapio domain) {
        ItemCardapioEntity entity = new ItemCardapioEntity();
        entity.setId(domain.getId());

        if (domain.getRestauranteId() != null) {
            RestauranteEntity restauranteEntity = new RestauranteEntity();
            restauranteEntity.setId(domain.getRestauranteId());
            entity.setRestaurante(restauranteEntity);
        }

        entity.setNome(domain.getNome());
        entity.setDescricao(domain.getDescricao());
        entity.setPreco(domain.getPreco());
        entity.setApenasLocal(domain.isApenasLocal());
        entity.setCaminhoFoto(domain.getCaminhoFoto());
        entity.setAtivo(domain.isAtivo());

        return entity;
    }

    private ItemCardapio toDomain(ItemCardapioEntity entity) {
        return new ItemCardapio(
                entity.getId(),
                entity.getRestaurante() != null ? entity.getRestaurante().getId() : null,
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.isApenasLocal(),
                entity.getCaminhoFoto(),
                entity.isAtivo()
        );
    }
}

