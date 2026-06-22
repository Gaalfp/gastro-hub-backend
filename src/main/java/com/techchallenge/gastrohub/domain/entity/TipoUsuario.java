package com.techchallenge.gastrohub.domain.entity;

import java.util.UUID;

public class TipoUsuario {
    private UUID id;
    private String nome;
    private boolean ativo;

    public TipoUsuario(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
        this.ativo = true;
    }

    public TipoUsuario(UUID id, String nome, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.ativo = ativo;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}