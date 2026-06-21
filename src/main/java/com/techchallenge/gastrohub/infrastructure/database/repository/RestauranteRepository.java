package com.techchallenge.gastrohub.infrastructure.database.repository;

import com.techchallenge.gastrohub.infrastructure.database.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestauranteRepository extends JpaRepository<RestauranteEntity, UUID> {
}