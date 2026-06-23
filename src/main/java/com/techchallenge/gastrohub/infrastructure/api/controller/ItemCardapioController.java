package com.techchallenge.gastrohub.infrastructure.api.controller;

import com.techchallenge.gastrohub.application.dto.ItemCardapioRequestDTO;
import com.techchallenge.gastrohub.application.dto.ItemCardapioResponseDTO;
import com.techchallenge.gastrohub.application.usecase.AtualizarItemCardapioUseCase;
import com.techchallenge.gastrohub.application.usecase.BuscarItensPorRestauranteUseCase;
import com.techchallenge.gastrohub.application.usecase.CriarItemCardapioUseCase;
import com.techchallenge.gastrohub.application.usecase.InativarItemCardapioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@Tag(name = "Itens do Cardápio", description = "Endpoints para gerenciamento dos itens do cardápio")
public class ItemCardapioController {

    private final CriarItemCardapioUseCase criarUseCase;
    private final BuscarItensPorRestauranteUseCase buscarUseCase;
    private final AtualizarItemCardapioUseCase atualizarUseCase;
    private final InativarItemCardapioUseCase inativarUseCase;

    public ItemCardapioController(CriarItemCardapioUseCase criarUseCase,
                                  BuscarItensPorRestauranteUseCase buscarUseCase,
                                  AtualizarItemCardapioUseCase atualizarUseCase,
                                  InativarItemCardapioUseCase inativarUseCase) {
        this.criarUseCase = criarUseCase;
        this.buscarUseCase = buscarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.inativarUseCase = inativarUseCase;
    }

    @Operation(summary = "Criar item do cardápio")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/restaurantes/{restauranteId}/itens")
    public ResponseEntity<ItemCardapioResponseDTO> criar(@PathVariable UUID restauranteId, @RequestBody ItemCardapioRequestDTO request) {
        var dto = new ItemCardapioRequestDTO(
                restauranteId,
                request.nome(),
                request.descricao(),
                request.preco(),
                request.apenasLocal(),
                request.caminhoFoto()
        );

        var response = criarUseCase.executar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar itens ativos de um restaurante")
    @GetMapping("/restaurantes/{restauranteId}/itens")
    public ResponseEntity<List<ItemCardapioResponseDTO>> listar(@PathVariable UUID restauranteId) {
        return ResponseEntity.ok(buscarUseCase.executar(restauranteId));
    }

    @Operation(summary = "Atualizar item do cardápio")
    @PutMapping("/itens/{id}")
    public ResponseEntity<ItemCardapioResponseDTO> atualizar(@PathVariable UUID id, @RequestBody ItemCardapioRequestDTO request) {
        var response = atualizarUseCase.executar(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Inativar (remover) item do cardápio")
    @DeleteMapping("/itens/{id}")
    public ResponseEntity<Void> inativar(@PathVariable UUID id) {
        inativarUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}

