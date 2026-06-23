package com.techchallenge.gastrohub.application.usecase.restaurante;

import com.techchallenge.gastrohub.application.gateway.RestauranteGateway;
import com.techchallenge.gastrohub.domain.entity.Restaurante;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DesativarRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private DesativarRestauranteUseCase useCase;

    @Test
    void deveDesativarRestauranteComSucesso() {
        UUID restauranteId = UUID.randomUUID();

        Restaurante restaurante = new Restaurante(
                restauranteId,
                "Cantina Italiana",
                "Rua das Pizzas, 123",
                "Italiana",
                "18:00 - 23:00",
                UUID.randomUUID(),
                true
        );

        when(restauranteGateway.buscarPorId(restauranteId))
                .thenReturn(Optional.of(restaurante));

        useCase.executar(restauranteId);

        assertThat(restaurante.isAtivo()).isFalse();

        verify(restauranteGateway).buscarPorId(restauranteId);
        verify(restauranteGateway).salvar(restaurante);
        verifyNoMoreInteractions(restauranteGateway);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        UUID restauranteId = UUID.randomUUID();

        when(restauranteGateway.buscarPorId(restauranteId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executar(restauranteId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Restaurante não encontrado com o ID informado.");

        verify(restauranteGateway).buscarPorId(restauranteId);
        verify(restauranteGateway, never()).salvar(any());
    }
}