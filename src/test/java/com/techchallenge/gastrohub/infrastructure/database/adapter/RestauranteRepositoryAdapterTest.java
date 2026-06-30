package com.techchallenge.gastrohub.infrastructure.database.adapter;

import com.techchallenge.gastrohub.domain.entity.Restaurante;
import com.techchallenge.gastrohub.infrastructure.database.entity.RestauranteEntity;
import com.techchallenge.gastrohub.infrastructure.database.entity.UsuarioEntity;
import com.techchallenge.gastrohub.infrastructure.database.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteRepositoryAdapterTest {

    @Mock
    private RestauranteRepository repository;

    @InjectMocks
    private RestauranteRepositoryAdapter adapter;

    private Restaurante restaurante;
    private RestauranteEntity restauranteEntity;
    private UUID restauranteId;
    private UUID donoId;

    @BeforeEach
    void setUp() {
        restauranteId = UUID.randomUUID();
        donoId = UUID.randomUUID();

        restaurante = new Restaurante(
                restauranteId,
                "Cantina",
                "Rua A, 123",
                "Italiana",
                "18:00 - 23:00",
                donoId,
                true
        );

        UsuarioEntity donoEntity = new UsuarioEntity();
        donoEntity.setId(donoId);

        restauranteEntity = new RestauranteEntity();
        restauranteEntity.setId(restauranteId);
        restauranteEntity.setNome("Cantina");
        restauranteEntity.setEndereco("Rua A, 123");
        restauranteEntity.setTipoCozinha("Italiana");
        restauranteEntity.setHorarioFuncionamento("18:00 - 23:00");
        restauranteEntity.setDono(donoEntity);
        restauranteEntity.setAtivo(true);
    }

    @Test
    void deveSalvarRestaurante() {
        when(repository.save(any(RestauranteEntity.class))).thenReturn(restauranteEntity);

        Restaurante resultado = adapter.salvar(restaurante);

        assertNotNull(resultado);
        assertEquals(restauranteId, resultado.getId());
        assertEquals("Cantina", resultado.getNome());
        assertEquals(donoId, resultado.getDonoId());
        verify(repository).save(any(RestauranteEntity.class));
    }

    @Test
    void deveBuscarRestaurantePorId() {
        when(repository.findById(restauranteId)).thenReturn(Optional.of(restauranteEntity));

        Optional<Restaurante> resultado = adapter.buscarPorId(restauranteId);

        assertTrue(resultado.isPresent());
        assertEquals(restauranteId, resultado.get().getId());
        assertEquals("Cantina", resultado.get().getNome());
    }

    @Test
    void deveRetornarVazioQuandoRestauranteNaoExiste() {
        when(repository.findById(restauranteId)).thenReturn(Optional.empty());

        Optional<Restaurante> resultado = adapter.buscarPorId(restauranteId);

        assertFalse(resultado.isPresent());
    }

    @Test
    void deveBuscarTodosRestaurantes() {
        Pageable pageable = Pageable.unpaged();
        Page<RestauranteEntity> page = new PageImpl<>(List.of(restauranteEntity));
        when(repository.findAll(pageable)).thenReturn(page);

        Page<Restaurante> resultado = adapter.buscarTodos(pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        assertEquals("Cantina", resultado.getContent().get(0).getNome());
    }

    @Test
    void deveRetornarPaginaVaziaQuandoNaoExistemRestaurantes() {
        Pageable pageable = Pageable.unpaged();
        Page<RestauranteEntity> page = new PageImpl<>(List.of());
        when(repository.findAll(pageable)).thenReturn(page);

        Page<Restaurante> resultado = adapter.buscarTodos(pageable);

        assertNotNull(resultado);
        assertEquals(0, resultado.getContent().size());
    }

    @Test
    void deveConvertarCorreatementeDominioPraEntity() {
        when(repository.save(any(RestauranteEntity.class))).thenReturn(restauranteEntity);

        adapter.salvar(restaurante);

        verify(repository).save(argThat(entity -> 
            entity.getNome().equals("Cantina") &&
            entity.getEndereco().equals("Rua A, 123") &&
            entity.getTipoCozinha().equals("Italiana") &&
            entity.getHorarioFuncionamento().equals("18:00 - 23:00")
        ));
    }

    @Test
    void deveConvertarCorreatementeEntityParaDominio() {
        when(repository.findById(restauranteId)).thenReturn(Optional.of(restauranteEntity));

        Optional<Restaurante> resultado = adapter.buscarPorId(restauranteId);

        assertTrue(resultado.isPresent());
        assertEquals(restaurante.getNome(), resultado.get().getNome());
        assertEquals(restaurante.getEndereco(), resultado.get().getEndereco());
        assertEquals(restaurante.getTipoCozinha(), resultado.get().getTipoCozinha());
    }

    @Test
    void devePreservarDonoIdAoConverter() {
        when(repository.findById(restauranteId)).thenReturn(Optional.of(restauranteEntity));

        Optional<Restaurante> resultado = adapter.buscarPorId(restauranteId);

        assertTrue(resultado.isPresent());
        assertEquals(donoId, resultado.get().getDonoId());
    }

    @Test
    void deveManterStatusAtivoAoSalvar() {
        when(repository.save(any(RestauranteEntity.class))).thenReturn(restauranteEntity);

        Restaurante resultado = adapter.salvar(restaurante);

        assertTrue(resultado.isAtivo());
    }
}
