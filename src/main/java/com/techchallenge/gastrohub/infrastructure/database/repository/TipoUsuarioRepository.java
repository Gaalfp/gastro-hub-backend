package com.techchallenge.gastrohub.infrastructure.persistence.repository;

import com.techchallenge.gastrohub.infrastructure.persistence.entity.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, UUID> {
}


