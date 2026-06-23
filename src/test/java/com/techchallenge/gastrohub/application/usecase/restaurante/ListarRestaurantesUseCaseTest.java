package com.techchallenge.gastrohub.application.usecase.restaurante;

import com.techchallenge.gastrohub.application.dto.RestauranteResponseDTO;
import com.techchallenge.gastrohub.application.gateway.RestauranteGateway;
import com.techchallenge.gastrohub.domain.entity.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarRestaurantesUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    private ListarRestaurantesUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ListarRestaurantesUseCase(restauranteGateway);
    }

    @Test
    void deveListarRestaurantesComSucesso() {
        Pageable pageable = PageRequest.of(0, 10);

        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();

        Restaurante restaurante = new Restaurante();
        restaurante.setId(id);
        restaurante.setNome("Cantina Italiana");
        restaurante.setEndereco("Rua das Pizzas, 123");
        restaurante.setTipoCozinha("Italiana");
        restaurante.setHorarioFuncionamento("18:00 - 23:00");
        restaurante.setDonoId(donoId);

        Page<Restaurante> paginaRestaurantes =
                new PageImpl<>(List.of(restaurante), pageable, 1);

        when(restauranteGateway.buscarTodos(pageable))
                .thenReturn(paginaRestaurantes);

        Page<RestauranteResponseDTO> resultado =
                useCase.executar(pageable);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getContent()).hasSize(1);

        RestauranteResponseDTO dto = resultado.getContent().get(0);

        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.nome()).isEqualTo("Cantina Italiana");
        assertThat(dto.endereco()).isEqualTo("Rua das Pizzas, 123");
        assertThat(dto.tipoCozinha()).isEqualTo("Italiana");
        assertThat(dto.horarioFuncionamento()).isEqualTo("18:00 - 23:00");
        assertThat(dto.donoId()).isEqualTo(donoId);

        verify(restauranteGateway).buscarTodos(pageable);
        verifyNoMoreInteractions(restauranteGateway);
    }

    @Test
    void deveRetornarPaginaVaziaQuandoNaoExistiremRestaurantes() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Restaurante> paginaVazia = Page.empty(pageable);

        when(restauranteGateway.buscarTodos(pageable))
                .thenReturn(paginaVazia);

        Page<RestauranteResponseDTO> resultado =
                useCase.executar(pageable);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getContent()).isEmpty();
        assertThat(resultado.getTotalElements()).isZero();

        verify(restauranteGateway).buscarTodos(pageable);
        verifyNoMoreInteractions(restauranteGateway);
    }
}