package com.techchallenge.gastrohub.infrastructure.api.controller;

import com.techchallenge.gastrohub.application.dto.TipoUsuarioRequestDTO;
import com.techchallenge.gastrohub.application.dto.TipoUsuarioResponseDTO;
import com.techchallenge.gastrohub.application.usecase.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techchallenge.gastrohub.infrastructure.controller.TipoUsuarioController;
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

@WebMvcTest(TipoUsuarioController.class)
@Import(GlobalExceptionHandler.class)
class TipoUsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;

    @MockBean
    private ListarTipoUsuarioUseCase listarTipoUsuarioUseCase;

    @MockBean
    private BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    @MockBean
    private AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;

    @MockBean
    private InativarTipoUsuarioUseCase inativarTipoUsuarioUseCase;

    @Test
    void deveCriarTipoUsuario() throws Exception {
        UUID id = UUID.randomUUID();

        TipoUsuarioRequestDTO request = new TipoUsuarioRequestDTO("Cliente");

        TipoUsuarioResponseDTO response = new TipoUsuarioResponseDTO(id, "Cliente");

        when(criarTipoUsuarioUseCase.executar(any())).thenReturn(response);

        mockMvc.perform(post("/tipos-usuario")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Cliente"));
    }

    @Test
    void deveListarTodosTiposUsuario() throws Exception {
        UUID id = UUID.randomUUID();

        TipoUsuarioResponseDTO dto = new TipoUsuarioResponseDTO(id, "Cliente");

        when(listarTipoUsuarioUseCase.executar()).thenReturn(List.of(dto));

        mockMvc.perform(get("/tipos-usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].nome").value("Cliente"));
    }

    @Test
    void deveBuscarTipoUsuarioPorId() throws Exception {
        UUID id = UUID.randomUUID();

        TipoUsuarioResponseDTO dto = new TipoUsuarioResponseDTO(id, "Cliente");

        when(buscarTipoUsuarioPorIdUseCase.executar(id)).thenReturn(dto);

        mockMvc.perform(get("/tipos-usuario/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void deveRetornar404QuandoTipoUsuarioNaoEncontrado() throws Exception {
        UUID id = UUID.randomUUID();

        when(buscarTipoUsuarioPorIdUseCase.executar(id))
                .thenThrow(new EntityNotFoundException("Tipo de usuário não encontrado com o ID informado."));

        mockMvc.perform(get("/tipos-usuario/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarTipoUsuario() throws Exception {
        UUID id = UUID.randomUUID();

        TipoUsuarioRequestDTO request = new TipoUsuarioRequestDTO("Dono");

        TipoUsuarioResponseDTO response = new TipoUsuarioResponseDTO(id, "Dono");

        when(atualizarTipoUsuarioUseCase.executar(eq(id), any())).thenReturn(response);

        mockMvc.perform(put("/tipos-usuario/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Dono"));
    }

    @Test
    void deveInativarTipoUsuario() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/tipos-usuario/{id}", id))
                .andExpect(status().isNoContent());

        verify(inativarTipoUsuarioUseCase).executar(id);
    }

    @Test
    void deveRetornar400QuandoJsonForInvalido() throws Exception {
        mockMvc.perform(post("/tipos-usuario")
                        .contentType(APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400AoBuscarComUuidInvalido() throws Exception {
        mockMvc.perform(get("/tipos-usuario/abc"))
                .andExpect(status().isBadRequest());
    }
}
