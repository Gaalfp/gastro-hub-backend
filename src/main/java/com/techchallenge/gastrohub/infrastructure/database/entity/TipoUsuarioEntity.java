package com.techchallenge.gastrohub.infrastructure.database.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tipo_usuario")
public class TipoUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    public TipoUsuarioEntity() {
    }

    public TipoUsuarioEntity(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}