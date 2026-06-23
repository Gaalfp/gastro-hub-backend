package com.techchallenge.gastrohub.infrastructure.database.repository;

import com.techchallenge.gastrohub.infrastructure.database.entity.ItemCardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemCardapioRepository extends JpaRepository<ItemCardapioEntity, UUID> {

    List<ItemCardapioEntity> findByRestauranteIdAndAtivoTrue(UUID restauranteId);
}