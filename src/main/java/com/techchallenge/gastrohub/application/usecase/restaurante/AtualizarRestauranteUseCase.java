package com.techchallenge.gastrohub.application.usecase.restaurante;

import com.techchallenge.gastrohub.application.dto.RestauranteRequestDTO;
import com.techchallenge.gastrohub.application.dto.RestauranteResponseDTO;
import com.techchallenge.gastrohub.application.gateway.RestauranteGateway;
import com.techchallenge.gastrohub.application.gateway.UsuarioGateway;
import com.techchallenge.gastrohub.domain.entity.Restaurante;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AtualizarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;
    private final UsuarioGateway usuarioGateway;

    public AtualizarRestauranteUseCase(RestauranteGateway restauranteGateway, UsuarioGateway usuarioGateway) {
        this.restauranteGateway = restauranteGateway;
        this.usuarioGateway = usuarioGateway;
    }

    @Transactional
    public RestauranteResponseDTO executar(UUID idRestaurante, RestauranteRequestDTO dto) {
        Restaurante restaurante = restauranteGateway.buscarPorId(idRestaurante)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado com o ID informado."));

        usuarioGateway.buscarPorId(dto.donoId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário (Dono) não encontrado com o ID informado."));

        restaurante.setNome(dto.nome());
        restaurante.setEndereco(dto.endereco());
        restaurante.setTipoCozinha(dto.tipoCozinha());
        restaurante.setHorarioFuncionamento(dto.horarioFuncionamento());
        restaurante.setDonoId(dto.donoId());

        return RestauranteResponseDTO.fromDomain(restauranteGateway.salvar(restaurante));
    }
}
