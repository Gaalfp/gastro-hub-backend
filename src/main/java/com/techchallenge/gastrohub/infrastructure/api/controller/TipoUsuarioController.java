package com.techchallenge.gastrohub.infrastructure.controller;

import com.techchallenge.gastrohub.application.dto.TipoUsuarioRequestDTO;
import com.techchallenge.gastrohub.application.dto.TipoUsuarioResponseDTO;
import com.techchallenge.gastrohub.application.usecase.*;
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
@RequestMapping("/tipos-usuario")
@Tag(name = "Tipos de Usuário", description = "Endpoints para o gerenciamento de categorias de usuários (ex: Cliente, Dono de Restaurante)")
public class TipoUsuarioController {

    private final CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;
    private final ListarTipoUsuarioUseCase listarTipoUsuarioUseCase;
    private final BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;
    private final AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;
    private final InativarTipoUsuarioUseCase inativarTipoUsuarioUseCase;

    public TipoUsuarioController(
            CriarTipoUsuarioUseCase criarTipoUsuarioUseCase,
            ListarTipoUsuarioUseCase listarTipoUsuarioUseCase,
            BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase,
            AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase,
            InativarTipoUsuarioUseCase inativarTipoUsuarioUseCase) {
        this.criarTipoUsuarioUseCase = criarTipoUsuarioUseCase;
        this.listarTipoUsuarioUseCase = listarTipoUsuarioUseCase;
        this.buscarTipoUsuarioPorIdUseCase = buscarTipoUsuarioPorIdUseCase;
        this.atualizarTipoUsuarioUseCase = atualizarTipoUsuarioUseCase;
        this.inativarTipoUsuarioUseCase = inativarTipoUsuarioUseCase;
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo tipo de usuário", description = "Cria uma nova categoria de perfil no sistema, como Cliente ou Dono.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<TipoUsuarioResponseDTO> criar(@RequestBody TipoUsuarioRequestDTO dto) {
        TipoUsuarioResponseDTO response = criarTipoUsuarioUseCase.executar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os tipos de usuários", description = "Retorna uma lista com todas as categorias cadastradas, ativas e inativas.")
    @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    public ResponseEntity<List<TipoUsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(listarTipoUsuarioUseCase.executar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de usuário por ID", description = "Busca os detalhes de uma categoria específica utilizando o seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    public ResponseEntity<TipoUsuarioResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(buscarTipoUsuarioPorIdUseCase.executar(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um tipo de usuário", description = "Modifica os dados de uma categoria existente utilizando o seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    public ResponseEntity<TipoUsuarioResponseDTO> atualizar(@PathVariable UUID id, @RequestBody TipoUsuarioRequestDTO dto) {
        return ResponseEntity.ok(atualizarTipoUsuarioUseCase.executar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inativar um tipo de usuário", description = "Realiza a exclusão lógica da categoria, alterando seu estado para inativo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de usuário inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable UUID id) {
        inativarTipoUsuarioUseCase.executar(id);
    }
}