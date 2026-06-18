-- Inserindo os Tipos de Usuário
INSERT INTO app.tipo_usuario (id, nome)
VALUES ('c1393666-41f2-4c23-8c44-11e2f7f98fc1', 'DONO_RESTAURANTE')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO app.tipo_usuario (id, nome)
VALUES ('b2484777-52e3-5d34-9d55-22f3e8e09ed2', 'CLIENTE')
    ON CONFLICT (id) DO NOTHING;

-- Inserindo o Usuário Administrador (Dono)
INSERT INTO app.usuario (id, nome, email, login, cpf, senha_hash, endereco, tipo_usuario_id, ativo)
VALUES (
           'a0282655-30e1-3b12-7b33-00d1e6e87eb0',
           'Administrador Teste',
           'admin@gastrohub.com.br',
           'admin',
           '12345678901',
           'hash_senha_aqui',
           'Rua das Flores, 123',
           'c1393666-41f2-4c23-8c44-11e2f7f98fc1',
           true
       ) ON CONFLICT (id) DO NOTHING;