package com.techchallenge.gastrohub.infrastructure.database.adapter;

import com.techchallenge.gastrohub.domain.entity.ItemCardapio;
import com.techchallenge.gastrohub.infrastructure.database.entity.ItemCardapioEntity;
import com.techchallenge.gastrohub.infrastructure.database.entity.RestauranteEntity;
import com.techchallenge.gastrohub.infrastructure.database.repository.ItemCardapioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCardapioRepositoryAdapterTest {

    @Mock
    private ItemCardapioRepository repository;

    @InjectMocks
    private ItemCardapioRepositoryAdapter adapter;

    private ItemCardapio itemCardapio;
    private ItemCardapioEntity itemCardapioEntity;
    private UUID itemId;
    private UUID restauranteId;

    @BeforeEach
    void setUp() {
        itemId = UUID.randomUUID();
        restauranteId = UUID.randomUUID();

        itemCardapio = new ItemCardapio(
                itemId,
                restauranteId,
                "Pasta Carbonara",
                "Massa com bacon",
                new BigDecimal("45.00"),
                false,
                "/fotos/carbonara.jpg",
                true
        );

        RestauranteEntity restauranteEntity = new RestauranteEntity();
        restauranteEntity.setId(restauranteId);

        itemCardapioEntity = new ItemCardapioEntity();
        itemCardapioEntity.setId(itemId);
        itemCardapioEntity.setRestaurante(restauranteEntity);
        itemCardapioEntity.setNome("Pasta Carbonara");
        itemCardapioEntity.setDescricao("Massa com bacon");
        itemCardapioEntity.setPreco(new BigDecimal("45.00"));
        itemCardapioEntity.setApenasLocal(false);
        itemCardapioEntity.setCaminhoFoto("/fotos/carbonara.jpg");
        itemCardapioEntity.setAtivo(true);
    }

    @Test
    void deveSalvarItem() {
        when(repository.save(any(ItemCardapioEntity.class))).thenReturn(itemCardapioEntity);

        ItemCardapio resultado = adapter.salvar(itemCardapio);

        assertNotNull(resultado);
        assertEquals(itemId, resultado.getId());
        assertEquals("Pasta Carbonara", resultado.getNome());
        assertEquals(restauranteId, resultado.getRestauranteId());
        verify(repository).save(any(ItemCardapioEntity.class));
    }

    @Test
    void deveBuscarItemPorId() {
        when(repository.findById(itemId)).thenReturn(Optional.of(itemCardapioEntity));

        Optional<ItemCardapio> resultado = adapter.buscarPorId(itemId);

        assertTrue(resultado.isPresent());
        assertEquals(itemId, resultado.get().getId());
        assertEquals("Pasta Carbonara", resultado.get().getNome());
    }

    @Test
    void deveRetornarVazioQuandoItemNaoExiste() {
        when(repository.findById(itemId)).thenReturn(Optional.empty());

        Optional<ItemCardapio> resultado = adapter.buscarPorId(itemId);

        assertFalse(resultado.isPresent());
    }

    @Test
    void deveBuscarItensPorRestauranteId() {
        List<ItemCardapioEntity> entities = List.of(itemCardapioEntity);
        when(repository.findByRestauranteIdAndAtivoTrue(restauranteId)).thenReturn(entities);

        List<ItemCardapio> resultado = adapter.buscarPorRestauranteId(restauranteId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pasta Carbonara", resultado.get(0).getNome());
    }

    @Test
    void deveRetornarListaVaziaQuandoRestauranteNaoTemItens() {
        when(repository.findByRestauranteIdAndAtivoTrue(restauranteId)).thenReturn(List.of());

        List<ItemCardapio> resultado = adapter.buscarPorRestauranteId(restauranteId);

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void deveConvertarCorreatmenteDominioPraEntity() {
        when(repository.save(any(ItemCardapioEntity.class))).thenReturn(itemCardapioEntity);

        adapter.salvar(itemCardapio);

        verify(repository).save(argThat(entity -> 
            entity.getNome().equals("Pasta Carbonara") &&
            entity.getDescricao().equals("Massa com bacon") &&
            entity.getPreco().equals(new BigDecimal("45.00")) &&
            entity.isAtivo() == true
        ));
    }

    @Test
    void deveConvertarCorreatementeEntityParaDominio() {
        when(repository.findById(itemId)).thenReturn(Optional.of(itemCardapioEntity));

        Optional<ItemCardapio> resultado = adapter.buscarPorId(itemId);

        assertTrue(resultado.isPresent());
        assertEquals(itemCardapio.getNome(), resultado.get().getNome());
        assertEquals(itemCardapio.getDescricao(), resultado.get().getDescricao());
        assertEquals(itemCardapio.getPreco(), resultado.get().getPreco());
    }
}
