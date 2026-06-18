package com.techchallenge.gastrohub.domain.entity;

import java.util.UUID;

public class Usuario {
    private UUID id;
    private String nome;
    private String email;
    private String login;
    private String cpf;
    private String senhaHash;
    private String endereco;
    private UUID tipoUsuarioId;
    private boolean ativo;

    public Usuario(UUID id, String nome, String email, String login, String cpf, String senhaHash, String endereco, UUID tipoUsuarioId, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.cpf = cpf;
        this.senhaHash = senhaHash;
        this.endereco = endereco;
        this.tipoUsuarioId = tipoUsuarioId;
        this.ativo = ativo;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getLogin() { return login; }
    public String getCpf() { return cpf; }
    public String getSenhaHash() { return senhaHash; }
    public String getEndereco() { return endereco; }
    public UUID getTipoUsuarioId() { return tipoUsuarioId; }
    public boolean isAtivo() { return ativo; }

    public void desativar() {
        this.ativo = false;
    }
}
