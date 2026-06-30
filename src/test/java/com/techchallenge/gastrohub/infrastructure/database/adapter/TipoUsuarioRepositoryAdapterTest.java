package com.techchallenge.gastrohub.infrastructure.database.adapter;

import com.techchallenge.gastrohub.domain.entity.TipoUsuario;
import com.techchallenge.gastrohub.infrastructure.database.entity.TipoUsuarioEntity;
import com.techchallenge.gastrohub.infrastructure.database.repository.TipoUsuarioRepository;
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
class TipoUsuarioRepositoryAdapterTest {

    @Mock
    private TipoUsuarioRepository repository;

    @InjectMocks
    private TipoUsuarioRepositoryAdapter adapter;

    private TipoUsuario tipoUsuario;
    private TipoUsuarioEntity tipoUsuarioEntity;
    private UUID tipoUsuarioId;

    @BeforeEach
    void setUp() {
        tipoUsuarioId = UUID.randomUUID();

        tipoUsuario = new TipoUsuario(tipoUsuarioId, "Cliente", true);

        tipoUsuarioEntity = new TipoUsuarioEntity();
        tipoUsuarioEntity.setId(tipoUsuarioId);
        tipoUsuarioEntity.setNome("Cliente");
        tipoUsuarioEntity.setAtivo(true);
    }

    @Test
    void deveSalvarTipoUsuario() {
        when(repository.save(any(TipoUsuarioEntity.class))).thenReturn(tipoUsuarioEntity);

        TipoUsuario resultado = adapter.salvar(tipoUsuario);

        assertNotNull(resultado);
        assertEquals(tipoUsuarioId, resultado.getId());
        assertEquals("Cliente", resultado.getNome());
        assertTrue(resultado.isAtivo());
        verify(repository).save(any(TipoUsuarioEntity.class));
    }

    @Test
    void deveBuscarTipoUsuarioPorId() {
        when(repository.findById(tipoUsuarioId)).thenReturn(Optional.of(tipoUsuarioEntity));

        Optional<TipoUsuario> resultado = adapter.buscarPorId(tipoUsuarioId);

        assertTrue(resultado.isPresent());
        assertEquals(tipoUsuarioId, resultado.get().getId());
        assertEquals("Cliente", resultado.get().getNome());
    }

    @Test
    void deveRetornarVazioQuandoTipoUsuarioNaoExiste() {
        when(repository.findById(tipoUsuarioId)).thenReturn(Optional.empty());

        Optional<TipoUsuario> resultado = adapter.buscarPorId(tipoUsuarioId);

        assertFalse(resultado.isPresent());
    }

    @Test
    void deveBuscarTodosTiposUsuario() {
        List<TipoUsuarioEntity> entities = List.of(tipoUsuarioEntity);
        when(repository.findAll()).thenReturn(entities);

        List<TipoUsuario> resultado = adapter.buscarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Cliente", resultado.get(0).getNome());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistemTiposUsuario() {
        when(repository.findAll()).thenReturn(List.of());

        List<TipoUsuario> resultado = adapter.buscarTodos();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void deveExcluirTipoUsuarioPorId() {
        adapter.excluir(tipoUsuarioId);

        verify(repository).deleteById(tipoUsuarioId);
    }

    @Test
    void deveConvertarCorreatementeDominioPraEntity() {
        when(repository.save(any(TipoUsuarioEntity.class))).thenReturn(tipoUsuarioEntity);

        adapter.salvar(tipoUsuario);

        verify(repository).save(argThat(entity -> 
            entity.getNome().equals("Cliente") &&
            entity.isAtivo() == true
        ));
    }

    @Test
    void deveConvertarCorreatementeEntityParaDominio() {
        when(repository.findById(tipoUsuarioId)).thenReturn(Optional.of(tipoUsuarioEntity));

        Optional<TipoUsuario> resultado = adapter.buscarPorId(tipoUsuarioId);

        assertTrue(resultado.isPresent());
        assertEquals(tipoUsuario.getNome(), resultado.get().getNome());
        assertEquals(tipoUsuario.isAtivo(), resultado.get().isAtivo());
    }

    @Test
    void deveSalvarTipoUsuarioInativo() {
        TipoUsuario tipoUsuarioInativo = new TipoUsuario(tipoUsuarioId, "Dono", false);
        TipoUsuarioEntity entityInativa = new TipoUsuarioEntity();
        entityInativa.setId(tipoUsuarioId);
        entityInativa.setNome("Dono");
        entityInativa.setAtivo(false);

        when(repository.save(any(TipoUsuarioEntity.class))).thenReturn(entityInativa);

        TipoUsuario resultado = adapter.salvar(tipoUsuarioInativo);

        assertFalse(resultado.isAtivo());
        assertEquals("Dono", resultado.getNome());
    }
}
