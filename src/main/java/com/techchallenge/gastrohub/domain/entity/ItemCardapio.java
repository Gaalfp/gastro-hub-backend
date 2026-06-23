package com.techchallenge.gastrohub.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class ItemCardapio {
    private UUID id;
    private UUID restauranteId;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean apenasLocal;
    private String caminhoFoto;
    private boolean ativo;

    public ItemCardapio(UUID id, UUID restauranteId, String nome, String descricao, BigDecimal preco, boolean apenasLocal, String caminhoFoto, boolean ativo) {
        this.id = id;
        this.restauranteId = restauranteId;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.apenasLocal = apenasLocal;
        this.caminhoFoto = caminhoFoto;
        this.ativo = ativo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(UUID restauranteId) {
        this.restauranteId = restauranteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public boolean isApenasLocal() {
        return apenasLocal;
    }

    public void setApenasLocal(boolean apenasLocal) {
        this.apenasLocal = apenasLocal;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void desativar() {
        this.ativo = false;
    }
}

