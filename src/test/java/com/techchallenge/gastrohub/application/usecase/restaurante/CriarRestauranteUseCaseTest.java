package com.techchallenge.gastrohub.application.usecase.restaurante;

import com.techchallenge.gastrohub.application.dto.RestauranteRequestDTO;
import com.techchallenge.gastrohub.application.dto.RestauranteResponseDTO;
import com.techchallenge.gastrohub.application.gateway.RestauranteGateway;
import com.techchallenge.gastrohub.application.gateway.UsuarioGateway;
import com.techchallenge.gastrohub.domain.entity.Restaurante;
import com.techchallenge.gastrohub.domain.entity.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    private CriarRestauranteUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CriarRestauranteUseCase(
                restauranteGateway,
                usuarioGateway
        );
    }

    @Test
    void deveCriarRestauranteComSucesso() {
        UUID donoId = UUID.randomUUID();
        UUID restauranteId = UUID.randomUUID();

        RestauranteRequestDTO dto = new RestauranteRequestDTO(
                "Cantina Italiana",
                "Rua das Pizzas, 123",
                "Italiana",
                "18:00 - 23:00",
                donoId
        );

        Usuario usuario = mock(Usuario.class);

        Restaurante restauranteSalvo = new Restaurante(
                restauranteId,
                dto.nome(),
                dto.endereco(),
                dto.tipoCozinha(),
                dto.horarioFuncionamento(),
                donoId,
                true
        );

        when(usuarioGateway.buscarPorId(donoId))
                .thenReturn(Optional.of(usuario));

        when(restauranteGateway.salvar(any(Restaurante.class)))
                .thenReturn(restauranteSalvo);

        RestauranteResponseDTO resultado = useCase.executar(dto);

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

        Restaurante restauranteCriado = captor.getValue();

        assertThat(restauranteCriado.getId()).isNull();
        assertThat(restauranteCriado.getNome()).isEqualTo(dto.nome());
        assertThat(restauranteCriado.getEndereco()).isEqualTo(dto.endereco());
        assertThat(restauranteCriado.getTipoCozinha()).isEqualTo(dto.tipoCozinha());
        assertThat(restauranteCriado.getHorarioFuncionamento()).isEqualTo(dto.horarioFuncionamento());
        assertThat(restauranteCriado.getDonoId()).isEqualTo(donoId);
        assertThat(restauranteCriado.isAtivo()).isTrue();

        verify(usuarioGateway).buscarPorId(donoId);
        verify(restauranteGateway).salvar(any(Restaurante.class));
        verifyNoMoreInteractions(restauranteGateway, usuarioGateway);
    }

    @Test
    void deveLancarExcecaoQuandoDonoNaoExistir() {
        UUID donoId = UUID.randomUUID();

        RestauranteRequestDTO dto = new RestauranteRequestDTO(
                "Cantina Italiana",
                "Rua das Pizzas, 123",
                "Italiana",
                "18:00 - 23:00",
                donoId
        );

        when(usuarioGateway.buscarPorId(donoId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executar(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuário (Dono) não encontrado com o ID informado.");

        verify(usuarioGateway).buscarPorId(donoId);
        verify(restauranteGateway, never()).salvar(any());
    }
}