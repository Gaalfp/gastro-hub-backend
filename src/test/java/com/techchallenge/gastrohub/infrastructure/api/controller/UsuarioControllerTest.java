package com.techchallenge.gastrohub.infrastructure.api.controller;

import com.techchallenge.gastrohub.application.dto.UsuarioRequestDTO;
import com.techchallenge.gastrohub.application.dto.UsuarioResponseDTO;
import com.techchallenge.gastrohub.application.usecase.usuario.AtualizarUsuarioUseCase;
import com.techchallenge.gastrohub.application.usecase.usuario.BuscarUsuarioUseCase;
import com.techchallenge.gastrohub.application.usecase.usuario.CriarUsuarioUseCase;
import com.techchallenge.gastrohub.application.usecase.usuario.InativarUsuarioUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techchallenge.gastrohub.infrastructure.exception.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@Import(GlobalExceptionHandler.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CriarUsuarioUseCase criarUsuarioUseCase;

    @MockBean
    private BuscarUsuarioUseCase buscarUsuarioUseCase;

    @MockBean
    private AtualizarUsuarioUseCase atualizarUsuarioUseCase;

    @MockBean
    private InativarUsuarioUseCase inativarUsuarioUseCase;

    @Test
    void deveCriarUsuario() throws Exception {
        UUID id = UUID.randomUUID();
        UUID tipoUsuarioId = UUID.randomUUID();

        UsuarioRequestDTO request = new UsuarioRequestDTO(
                "João Silva",
                "joao@email.com",
                "joao123",
                "12345678900",
                "senha123",
                "Rua A, 123",
                tipoUsuarioId
        );

        UsuarioResponseDTO response = new UsuarioResponseDTO(
                id,
                "João Silva",
                "joao@email.com",
                "joao123",
                "12345678900",
                "Rua A, 123",
                tipoUsuarioId,
                true
        );

        when(criarUsuarioUseCase.executar(any())).thenReturn(response);

        mockMvc.perform(post("/usuarios")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    void deveListarTodosOsUsuarios() throws Exception {
        UUID id = UUID.randomUUID();
        UUID tipoUsuarioId = UUID.randomUUID();

        UsuarioResponseDTO dto = new UsuarioResponseDTO(
                id,
                "João Silva",
                "joao@email.com",
                "joao123",
                "12345678900",
                "Rua A, 123",
                tipoUsuarioId,
                true
        );

        when(buscarUsuarioUseCase.buscarTodos()).thenReturn(List.of(dto));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].nome").value("João Silva"));
    }

    @Test
    void deveBuscarUsuarioPorId() throws Exception {
        UUID id = UUID.randomUUID();
        UUID tipoUsuarioId = UUID.randomUUID();

        UsuarioResponseDTO dto = new UsuarioResponseDTO(
                id,
                "João Silva",
                "joao@email.com",
                "joao123",
                "12345678900",
                "Rua A, 123",
                tipoUsuarioId,
                true
        );

        when(buscarUsuarioUseCase.buscarPorId(id)).thenReturn(dto);

        mockMvc.perform(get("/usuarios/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void deveRetornar404QuandoUsuarioNaoEncontrado() throws Exception {
        UUID id = UUID.randomUUID();

        when(buscarUsuarioUseCase.buscarPorId(id))
                .thenThrow(new EntityNotFoundException("Usuário não encontrado com o ID informado."));

        mockMvc.perform(get("/usuarios/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarUsuario() throws Exception {
        UUID id = UUID.randomUUID();
        UUID tipoUsuarioId = UUID.randomUUID();

        UsuarioRequestDTO request = new UsuarioRequestDTO(
                "João Silva Atualizado",
                "joao.atualizado@email.com",
                "joao123",
                "12345678900",
                "senha123",
                "Rua B, 456",
                tipoUsuarioId
        );

        UsuarioResponseDTO response = new UsuarioResponseDTO(
                id,
                "João Silva Atualizado",
                "joao.atualizado@email.com",
                "joao123",
                "12345678900",
                "Rua B, 456",
                tipoUsuarioId,
                true
        );

        when(atualizarUsuarioUseCase.executar(eq(id), any())).thenReturn(response);

        mockMvc.perform(put("/usuarios/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva Atualizado"));
    }

    @Test
    void deveInativarUsuario() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/usuarios/{id}", id))
                .andExpect(status().isNoContent());

        verify(inativarUsuarioUseCase).executar(id);
    }

    @Test
    void deveRetornar400QuandoJsonForInvalido() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .contentType(APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400AoAtualizarComUuidInvalido() throws Exception {
        mockMvc.perform(get("/usuarios/abc"))
                .andExpect(status().isBadRequest());
    }
}
