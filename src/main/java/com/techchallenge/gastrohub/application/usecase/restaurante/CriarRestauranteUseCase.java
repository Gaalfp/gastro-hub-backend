package com.techchallenge.gastrohub.application.usecase.restaurante;

import com.techchallenge.gastrohub.application.dto.RestauranteRequestDTO;
import com.techchallenge.gastrohub.application.dto.RestauranteResponseDTO;
import com.techchallenge.gastrohub.application.gateway.RestauranteGateway;
import com.techchallenge.gastrohub.application.gateway.UsuarioGateway;
import com.techchallenge.gastrohub.domain.entity.Restaurante;
import jakarta.persistence.EntityNotFoundException;

public class CriarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;
    private final UsuarioGateway usuarioGateway;

    public CriarRestauranteUseCase(RestauranteGateway restauranteGateway, UsuarioGateway usuarioGateway) {
        this.restauranteGateway = restauranteGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public RestauranteResponseDTO executar(RestauranteRequestDTO dto) {
        usuarioGateway.buscarPorId(dto.donoId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário (Dono) não encontrado com o ID informado."));

        Restaurante novoRestaurante = new Restaurante(
                null,
                dto.nome(),
                dto.endereco(),
                dto.tipoCozinha(),
                dto.horarioFuncionamento(),
                dto.donoId(),
                true
        );

        Restaurante restauranteSalvo = restauranteGateway.salvar(novoRestaurante);

        return RestauranteResponseDTO.fromDomain(restauranteSalvo);
    }
}