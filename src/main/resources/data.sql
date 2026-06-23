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

-- Inserindo um restaurante exemplo vinculado ao usuário administrador
INSERT INTO app.restaurante (id, nome, endereco, tipo_cozinha, horario_funcionamento, dono_id, ativo)
VALUES (
    'd1111111-2222-3333-4444-555555555555',
    'Cantina do Teste',
    'Av. dos Exemplos, 100',
    'Italiana',
    '18:00 - 23:00',
    'a0282655-30e1-3b12-7b33-00d1e6e87eb0',
    true
) ON CONFLICT (id) DO NOTHING;

-- Inserindo alguns itens de cardápio para o restaurante de exemplo
INSERT INTO app.item_cardapio (id, restaurante_id, nome, descricao, preco, apenas_local, caminho_foto, ativo)
VALUES (
    'e1111111-2222-3333-4444-666666666666',
    'd1111111-2222-3333-4444-555555555555',
    'Lasanha à Bolonhesa',
    'Lasanha caseira com molho bolonhesa e queijo gratinado',
    39.90,
    false,
    '/imagens/lasanha.jpg',
    true
) ON CONFLICT (id) DO NOTHING;

INSERT INTO app.item_cardapio (id, restaurante_id, nome, descricao, preco, apenas_local, caminho_foto, ativo)
VALUES (
    'e2222222-2222-3333-4444-777777777777',
    'd1111111-2222-3333-4444-555555555555',
    'Risoto de Camarão',
    'Risoto cremoso de camarão com toque de limão siciliano',
    49.50,
    true,
    '/imagens/risoto.jpg',
    true
) ON CONFLICT (id) DO NOTHING;
