package com.techchallenge.gastrohub.infrastructure.controller;

import com.techchallenge.gastrohub.application.dto.RestauranteRequestDTO;
import com.techchallenge.gastrohub.application.dto.RestauranteResponseDTO;
import com.techchallenge.gastrohub.application.usecase.CriarRestauranteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "Endpoints para o gerenciamento de restaurantes")
public class RestauranteController {

    private final CriarRestauranteUseCase criarRestauranteUseCase;

    public RestauranteController(CriarRestauranteUseCase criarRestauranteUseCase) {
        this.criarRestauranteUseCase = criarRestauranteUseCase;
    }

    @Operation(summary = "Criar um novo restaurante", description = "Registra um novo restaurante no sistema vinculado a um usuário dono.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário não encontrado")
    })
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> criar(@RequestBody RestauranteRequestDTO requestDTO) {
        RestauranteResponseDTO response = criarRestauranteUseCase.executar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}