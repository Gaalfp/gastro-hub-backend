package com.techchallenge.gastrohub.infrastructure.api.controller;

import com.techchallenge.gastrohub.application.dto.UsuarioRequestDTO;
import com.techchallenge.gastrohub.application.dto.UsuarioResponseDTO;
import com.techchallenge.gastrohub.application.usecase.AtualizarUsuarioUseCase;
import com.techchallenge.gastrohub.application.usecase.BuscarUsuarioUseCase;
import com.techchallenge.gastrohub.application.usecase.CriarUsuarioUseCase;
import com.techchallenge.gastrohub.application.usecase.InativarUsuarioUseCase;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para o gerenciamento de usuários (Clientes e Donos de Restaurante)")
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final BuscarUsuarioUseCase buscarUsuarioUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final InativarUsuarioUseCase inativarUsuarioUseCase;

    public UsuarioController(CriarUsuarioUseCase criarUsuarioUseCase,
                             BuscarUsuarioUseCase buscarUsuarioUseCase,
                             AtualizarUsuarioUseCase atualizarUsuarioUseCase,
                             InativarUsuarioUseCase inativarUsuarioUseCase) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.buscarUsuarioUseCase = buscarUsuarioUseCase;
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
        this.inativarUsuarioUseCase = inativarUsuarioUseCase;
    }

    @Operation(summary = "Criar um novo usuário", description = "Cadastra um novo usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@RequestBody UsuarioRequestDTO requestDTO) {
        UsuarioResponseDTO response = criarUsuarioUseCase.executar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista contendo todos os usuários cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(buscarUsuarioUseCase.buscarTodos());
    }

    @Operation(summary = "Buscar usuário por ID", description = "Busca os detalhes de um usuário específico utilizando o seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado") // Previsão para quando criarmos o handler de exceções
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(buscarUsuarioUseCase.buscarPorId(id));
    }

    @Operation(summary = "Atualizar um usuário")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable UUID id, @RequestBody UsuarioRequestDTO requestDTO) {
        return ResponseEntity.ok(atualizarUsuarioUseCase.executar(id, requestDTO));
    }

    @Operation(summary = "Inativar um usuário", description = "Realiza a exclusão lógica do usuário.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable UUID id) {
        inativarUsuarioUseCase.executar(id);
    }
}