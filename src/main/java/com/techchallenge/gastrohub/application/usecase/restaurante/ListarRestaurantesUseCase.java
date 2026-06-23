package com.techchallenge.gastrohub.application.usecase.restaurante;

import com.techchallenge.gastrohub.application.dto.RestauranteResponseDTO;
import com.techchallenge.gastrohub.application.gateway.RestauranteGateway;
import com.techchallenge.gastrohub.domain.entity.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarRestaurantesUseCase {

    private final RestauranteGateway restauranteGateway;

    public ListarRestaurantesUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public Page<RestauranteResponseDTO> executar(Pageable pageable) {
        Page<Restaurante> restaurantes = restauranteGateway.buscarTodos(pageable);
        return restaurantes.map(RestauranteResponseDTO::fromDomain);
    }
}
