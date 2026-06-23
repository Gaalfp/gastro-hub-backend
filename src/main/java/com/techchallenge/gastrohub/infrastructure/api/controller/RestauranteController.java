package com.techchallenge.gastrohub.infrastructure.api.controller;

import com.techchallenge.gastrohub.application.dto.RestauranteRequestDTO;
import com.techchallenge.gastrohub.application.dto.RestauranteResponseDTO;
import com.techchallenge.gastrohub.application.usecase.restaurante.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "Endpoints para o gerenciamento de restaurantes")
public class RestauranteController {

    private final CriarRestauranteUseCase criarRestauranteUseCase;
    private final ListarRestaurantesUseCase listarRestaurantesUseCase;
    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;
    private final AtualizarRestauranteUseCase atualizarRestauranteUseCase;
    private final DesativarRestauranteUseCase desativarRestauranteUseCase;
    private final AtivarRestauranteUseCase ativarRestauranteUseCase;

    public RestauranteController(CriarRestauranteUseCase criarRestauranteUseCase, ListarRestaurantesUseCase listarRestaurantesUseCase, BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase, AtualizarRestauranteUseCase atualizarRestauranteUseCase, DesativarRestauranteUseCase desativarRestauranteUseCase, AtivarRestauranteUseCase ativarRestauranteUseCase) {
        this.criarRestauranteUseCase = criarRestauranteUseCase;
        this.listarRestaurantesUseCase = listarRestaurantesUseCase;
        this.buscarRestaurantePorIdUseCase = buscarRestaurantePorIdUseCase;
        this.atualizarRestauranteUseCase = atualizarRestauranteUseCase;
        this.desativarRestauranteUseCase = desativarRestauranteUseCase;
        this.ativarRestauranteUseCase = ativarRestauranteUseCase;
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

    @Operation(
            summary = "Listar restaurantes",
            description = "Retorna uma lista paginada de restaurantes cadastrados no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurantes listados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros de paginação inválidos")
    })
    @GetMapping
    public ResponseEntity<Page<RestauranteResponseDTO>> listarRestaurantes(@PageableDefault(size = 10) Pageable pageable) {
        Page<RestauranteResponseDTO> response = listarRestaurantesUseCase.executar(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Buscar restaurante por ID",
            description = "Retorna os dados de um restaurante específico a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "400", description = "ID informado é inválido")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorId(@PathVariable UUID id) {
        RestauranteResponseDTO response = buscarRestaurantePorIdUseCase.executar(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Atualizar restaurante",
            description = "Atualiza os dados de um restaurante existente a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou ID informado é inválido"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> atualizar(@PathVariable UUID id, @RequestBody RestauranteRequestDTO requestDTO) {
        RestauranteResponseDTO response = atualizarRestauranteUseCase.executar(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Desativar restaurante",
            description = "Realiza a desativação lógica de um restaurante a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurante desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "400", description = "ID informado é inválido")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable UUID id) {
        desativarRestauranteUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Ativar restaurante",
            description = "Reativa um restaurante previamente desativado a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "400", description = "ID informado é inválido")
    })
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable UUID id) {
        ativarRestauranteUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}