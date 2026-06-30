package com.techchallenge.gastrohub.infrastructure.database.adapter;

import com.techchallenge.gastrohub.domain.entity.Usuario;
import com.techchallenge.gastrohub.infrastructure.database.entity.TipoUsuarioEntity;
import com.techchallenge.gastrohub.infrastructure.database.entity.UsuarioEntity;
import com.techchallenge.gastrohub.infrastructure.database.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioRepositoryAdapterTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioRepositoryAdapter adapter;

    private Usuario usuario;
    private UsuarioEntity usuarioEntity;
    private UUID usuarioId;
    private UUID tipoUsuarioId;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        tipoUsuarioId = UUID.randomUUID();

        usuario = new Usuario(
                usuarioId,
                "João Silva",
                "joao@email.com",
                "joao123",
                "12345678900",
                "hash123",
                "Rua A, 123",
                tipoUsuarioId,
                true
        );

        TipoUsuarioEntity tipoUsuarioEntity = new TipoUsuarioEntity();
        tipoUsuarioEntity.setId(tipoUsuarioId);

        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(usuarioId);
        usuarioEntity.setNome("João Silva");
        usuarioEntity.setEmail("joao@email.com");
        usuarioEntity.setLogin("joao123");
        usuarioEntity.setCpf("12345678900");
        usuarioEntity.setSenhaHash("hash123");
        usuarioEntity.setEndereco("Rua A, 123");
        usuarioEntity.setTipoUsuario(tipoUsuarioEntity);
        usuarioEntity.setAtivo(true);
    }

    @Test
    void deveSalvarUsuario() {
        when(repository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        Usuario resultado = adapter.salvar(usuario);

        assertNotNull(resultado);
        assertEquals(usuarioId, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals(tipoUsuarioId, resultado.getTipoUsuarioId());
        verify(repository).save(any(UsuarioEntity.class));
    }

    @Test
    void deveBuscarUsuarioPorId() {
        when(repository.findById(usuarioId)).thenReturn(Optional.of(usuarioEntity));

        Optional<Usuario> resultado = adapter.buscarPorId(usuarioId);

        assertTrue(resultado.isPresent());
        assertEquals(usuarioId, resultado.get().getId());
        assertEquals("João Silva", resultado.get().getNome());
    }

    @Test
    void deveRetornarVazioQuandoUsuarioNaoExiste() {
        when(repository.findById(usuarioId)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = adapter.buscarPorId(usuarioId);

        assertFalse(resultado.isPresent());
    }

    @Test
    void deveBuscarTodosOsUsuarios() {
        List<UsuarioEntity> entities = List.of(usuarioEntity);
        when(repository.findAll()).thenReturn(entities);

        List<Usuario> resultado = adapter.buscarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistemUsuarios() {
        when(repository.findAll()).thenReturn(List.of());

        List<Usuario> resultado = adapter.buscarTodos();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void deveConvertarCorreatmenteDominioPraEntity() {
        when(repository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        adapter.salvar(usuario);

        verify(repository).save(argThat(entity -> 
            entity.getNome().equals("João Silva") &&
            entity.getEmail().equals("joao@email.com") &&
            entity.getLogin().equals("joao123") &&
            entity.getCpf().equals("12345678900")
        ));
    }

    @Test
    void deveConvertarCorreatementeEntityParaDominio() {
        when(repository.findById(usuarioId)).thenReturn(Optional.of(usuarioEntity));

        Optional<Usuario> resultado = adapter.buscarPorId(usuarioId);

        assertTrue(resultado.isPresent());
        assertEquals(usuario.getNome(), resultado.get().getNome());
        assertEquals(usuario.getEmail(), resultado.get().getEmail());
        assertEquals(usuario.getLogin(), resultado.get().getLogin());
        assertEquals(usuario.getCpf(), resultado.get().getCpf());
    }

    @Test
    void devePreservarTipoUsuarioIdAoConverter() {
        when(repository.findById(usuarioId)).thenReturn(Optional.of(usuarioEntity));

        Optional<Usuario> resultado = adapter.buscarPorId(usuarioId);

        assertTrue(resultado.isPresent());
        assertEquals(tipoUsuarioId, resultado.get().getTipoUsuarioId());
    }
}
