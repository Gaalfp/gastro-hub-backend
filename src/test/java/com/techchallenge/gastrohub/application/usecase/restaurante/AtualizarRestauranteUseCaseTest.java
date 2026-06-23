package com.techchallenge.gastrohub.application.usecase.restaurante;

import com.techchallenge.gastrohub.application.dto.RestauranteRequestDTO;
import com.techchallenge.gastrohub.application.dto.RestauranteResponseDTO;
import com.techchallenge.gastrohub.application.gateway.RestauranteGateway;
import com.techchallenge.gastrohub.application.gateway.UsuarioGateway;
import com.techchallenge.gastrohub.domain.entity.Restaurante;
import com.techchallenge.gastrohub.domain.entity.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private AtualizarRestauranteUseCase useCase;

    @Test
    void deveAtualizarRestauranteComSucesso() {
        UUID restauranteId = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();

        Restaurante restaurante = new Restaurante(
                restauranteId,
                "Restaurante Antigo",
                "Endereço Antigo",
                "Brasileira",
                "08:00 - 18:00",
                UUID.randomUUID(),
                true
        );

        RestauranteRequestDTO dto = new RestauranteRequestDTO(
                "Cantina Italiana",
                "Rua das Pizzas, 123",
                "Italiana",
                "18:00 - 23:00",
                donoId
        );

        Usuario usuario = mock(Usuario.class);

        when(restauranteGateway.buscarPorId(restauranteId))
                .thenReturn(Optional.of(restaurante));

        when(usuarioGateway.buscarPorId(donoId))
                .thenReturn(Optional.of(usuario));

        when(restauranteGateway.salvar(any(Restaurante.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RestauranteResponseDTO resultado =
                useCase.executar(restauranteId, dto);

        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(restauranteId);
        assertThat(resultado.nome()).isEqualTo("Cantina Italiana");
        assertThat(resultado.endereco()).isEqualTo("Rua das Pizzas, 123");
        assertThat(resultado.tipoCozinha()).isEqualTo("Italiana");
        assertThat(resultado.horarioFuncionamento()).isEqualTo("18:00 - 23:00");
        assertThat(resultado.donoId()).isEqualTo(donoId);

        ArgumentCaptor<Restaurante> captor =
                ArgumentCaptor.forClass(Restaurante.class);

        verify(restauranteGateway).salvar(captor.capture());

        Restaurante restauranteAtualizado = captor.getValue();

        assertThat(restauranteAtualizado.getNome())
                .isEqualTo(dto.nome());
        assertThat(restauranteAtualizado.getEndereco())
                .isEqualTo(dto.endereco());
        assertThat(restauranteAtualizado.getTipoCozinha())
                .isEqualTo(dto.tipoCozinha());
        assertThat(restauranteAtualizado.getHorarioFuncionamento())
                .isEqualTo(dto.horarioFuncionamento());
        assertThat(restauranteAtualizado.getDonoId())
                .isEqualTo(dto.donoId());

        verify(restauranteGateway).buscarPorId(restauranteId);
        verify(usuarioGateway).buscarPorId(donoId);
        verify(restauranteGateway).salvar(restaurante);
        verifyNoMoreInteractions(restauranteGateway, usuarioGateway);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        UUID restauranteId = UUID.randomUUID();

        RestauranteRequestDTO dto = new RestauranteRequestDTO(
                "Cantina Italiana",
                "Rua das Pizzas, 123",
                "Italiana",
                "18:00 - 23:00",
                UUID.randomUUID()
        );

        when(restauranteGateway.buscarPorId(restauranteId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                useCase.executar(restauranteId, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Restaurante não encontrado com o ID informado.");

        verify(restauranteGateway).buscarPorId(restauranteId);
        verify(usuarioGateway, never()).buscarPorId(any());
        verify(restauranteGateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoDonoNaoEncontrado() {
        UUID restauranteId = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();

        Restaurante restaurante = new Restaurante(
                restauranteId,
                "Restaurante",
                "Endereço",
                "Brasileira",
                "08:00 - 18:00",
                UUID.randomUUID(),
                true
        );

        RestauranteRequestDTO dto = new RestauranteRequestDTO(
                "Cantina Italiana",
                "Rua das Pizzas, 123",
                "Italiana",
                "18:00 - 23:00",
                donoId
        );

        when(restauranteGateway.buscarPorId(restauranteId))
                .thenReturn(Optional.of(restaurante));

        when(usuarioGateway.buscarPorId(donoId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                useCase.executar(restauranteId, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuário (Dono) não encontrado com o ID informado.");

        verify(restauranteGateway).buscarPorId(restauranteId);
        verify(usuarioGateway).buscarPorId(donoId);
        verify(restauranteGateway, never()).salvar(any());
    }
}