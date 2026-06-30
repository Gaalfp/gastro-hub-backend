package com.techchallenge.gastrohub.infrastructure.config;

import com.techchallenge.gastrohub.application.gateway.*;
import com.techchallenge.gastrohub.application.usecase.itemcardapio.AtualizarItemCardapioUseCase;
import com.techchallenge.gastrohub.application.usecase.itemcardapio.CriarItemCardapioUseCase;
import com.techchallenge.gastrohub.application.usecase.itemcardapio.InativarItemCardapioUseCase;
import com.techchallenge.gastrohub.application.usecase.restaurante.*;
import com.techchallenge.gastrohub.application.usecase.usuario.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CriarUsuarioUseCase criarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        return new CriarUsuarioUseCase(usuarioGateway);
    }

    @Bean
    public AtualizarUsuarioUseCase atualizarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        return new AtualizarUsuarioUseCase(usuarioGateway);
    }

    @Bean
    public BuscarUsuarioUseCase buscarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        return new BuscarUsuarioUseCase(usuarioGateway);
    }

    @Bean
    public InativarUsuarioUseCase inativarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        return new InativarUsuarioUseCase(usuarioGateway);
    }

    @Bean
    public CriarTipoUsuarioUseCase criarTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        return new CriarTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    @Bean
    public AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        return new AtualizarTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    @Bean
    public BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        return new BuscarTipoUsuarioPorIdUseCase(tipoUsuarioGateway);
    }

    @Bean
    public ListarTipoUsuarioUseCase listarTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        return new ListarTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    @Bean
    public InativarTipoUsuarioUseCase inativarTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        return new InativarTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    @Bean
    public CriarRestauranteUseCase criarRestauranteUseCase(
            RestauranteGateway restauranteGateway,
            UsuarioGateway usuarioGateway) {
        return new CriarRestauranteUseCase(restauranteGateway, usuarioGateway);
    }

    @Bean
    public AtualizarRestauranteUseCase atualizarRestauranteUseCase(
            RestauranteGateway restauranteGateway,
            UsuarioGateway usuarioGateway) {
        return new AtualizarRestauranteUseCase(restauranteGateway, usuarioGateway);
    }

    @Bean
    public BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase(RestauranteGateway restauranteGateway) {
        return new BuscarRestaurantePorIdUseCase(restauranteGateway);
    }

    @Bean
    public ListarRestaurantesUseCase listarRestaurantesUseCase(RestauranteGateway restauranteGateway) {
        return new ListarRestaurantesUseCase(restauranteGateway);
    }

    @Bean
    public AtivarRestauranteUseCase ativarRestauranteUseCase(RestauranteGateway restauranteGateway) {
        return new AtivarRestauranteUseCase(restauranteGateway);
    }

    @Bean
    public DesativarRestauranteUseCase desativarRestauranteUseCase(RestauranteGateway restauranteGateway) {
        return new DesativarRestauranteUseCase(restauranteGateway);
    }

    @Bean
    public CriarItemCardapioUseCase criarItemCardapioUseCase(
            ItemCardapioGateway itemCardapioGateway,
            RestauranteGateway restauranteGateway) {
        return new CriarItemCardapioUseCase(itemCardapioGateway, restauranteGateway);
    }

    @Bean
    public AtualizarItemCardapioUseCase atualizarItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway) {
        return new AtualizarItemCardapioUseCase(itemCardapioGateway);
    }

    @Bean
    public AtualizarItemCardapioUseCase.BuscarItensPorRestauranteUseCase buscarItensPorRestauranteUseCase(ItemCardapioGateway itemCardapioGateway) {
        return new AtualizarItemCardapioUseCase.BuscarItensPorRestauranteUseCase(itemCardapioGateway);
    }

    @Bean
    public InativarItemCardapioUseCase inativarItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway) {
        return new InativarItemCardapioUseCase(itemCardapioGateway);
    }
}
