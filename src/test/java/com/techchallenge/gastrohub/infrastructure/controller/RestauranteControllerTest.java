package com.techchallenge.gastrohub.infrastructure.controller;

import com.techchallenge.gastrohub.application.dto.RestauranteRequestDTO;
import com.techchallenge.gastrohub.application.dto.RestauranteResponseDTO;
import com.techchallenge.gastrohub.application.usecase.restaurante.*;
import com.techchallenge.gastrohub.infrastructure.api.controller.RestauranteController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techchallenge.gastrohub.infrastructure.exception.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestauranteController.class)
@Import(GlobalExceptionHandler.class)
class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CriarRestauranteUseCase criarRestauranteUseCase;

    @MockBean
    private ListarRestaurantesUseCase listarRestaurantesUseCase;

    @MockBean
    private BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;

    @MockBean
    private AtualizarRestauranteUseCase atualizarRestauranteUseCase;

    @MockBean
    private DesativarRestauranteUseCase desativarRestauranteUseCase;

    @MockBean
    private AtivarRestauranteUseCase ativarRestauranteUseCase;

    @Test
    void deveCriarRestaurante() throws Exception {
        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();

        RestauranteRequestDTO request =
                new RestauranteRequestDTO(
                        "Cantina",
                        "Rua A",
                        "Italiana",
                        "18:00 - 23:00",
                        donoId
                );

        RestauranteResponseDTO response =
                new RestauranteResponseDTO(
                        id,
                        "Cantina",
                        "Rua A",
                        "Italiana",
                        "18:00 - 23:00",
                        donoId
                );

        when(criarRestauranteUseCase.executar(any()))
                .thenReturn(response);

        mockMvc.perform(post("/restaurantes")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Cantina"));
    }

    @Test
    void deveRetornar404AoCriarRestauranteQuandoDonoNaoExistir() throws Exception {
        UUID donoId = UUID.randomUUID();
        RestauranteRequestDTO request = new RestauranteRequestDTO("Cantina", "Rua A", "Italiana", "18:00 - 23:00", donoId);

        when(criarRestauranteUseCase.executar(any()))
                .thenThrow(new EntityNotFoundException("Usuário (Dono) não encontrado com o ID informado."));

        mockMvc.perform(post("/restaurantes")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Usuário (Dono) não encontrado com o ID informado."));
    }

    @Test
    void deveRetornar400QuandoJsonForInvalido() throws Exception {
        mockMvc.perform(post("/restaurantes")
                        .contentType(APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Verifique se o JSON enviado está correto e bem formatado."));
    }

    @Test
    void deveListarRestaurantes() throws Exception {
        UUID id = UUID.randomUUID();
        RestauranteResponseDTO dto = new RestauranteResponseDTO(id, "Cantina", "Rua A", "Italiana", "18:00 - 23:00", UUID.randomUUID());

        when(listarRestaurantesUseCase.executar(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        mockMvc.perform(get("/restaurantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(id.toString()));
    }

    @Test
    void deveBuscarRestaurantePorId() throws Exception {
        UUID id = UUID.randomUUID();
        RestauranteResponseDTO dto = new RestauranteResponseDTO(id, "Cantina", "Rua A", "Italiana", "18:00 - 23:00", UUID.randomUUID());

        when(buscarRestaurantePorIdUseCase.executar(id)).thenReturn(dto);

        mockMvc.perform(get("/restaurantes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void deveRetornar404QuandoRestauranteNaoEncontrado() throws Exception {
        UUID id = UUID.randomUUID();
        when(buscarRestaurantePorIdUseCase.executar(id))
                .thenThrow(new EntityNotFoundException("Restaurante não encontrado com o ID informado."));

        mockMvc.perform(get("/restaurantes/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Restaurante não encontrado com o ID informado."));
    }

    @Test
    void deveRetornar400QuandoUuidForInvalido() throws Exception {
        mockMvc.perform(get("/restaurantes/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveAtualizarRestaurante() throws Exception {
        UUID id = UUID.randomUUID();
        UUID donoId = UUID.randomUUID();
        RestauranteRequestDTO request = new RestauranteRequestDTO("Novo", "Rua Nova", "Japonesa", "10:00 - 22:00", donoId);
        RestauranteResponseDTO response = new RestauranteResponseDTO(id, request.nome(), request.endereco(), request.tipoCozinha(), request.horarioFuncionamento(), donoId);

        when(atualizarRestauranteUseCase.executar(eq(id), any())).thenReturn(response);

        mockMvc.perform(put("/restaurantes/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo"));
    }

    @Test
    void deveDesativarRestaurante() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/restaurantes/{id}", id)).andExpect(status().isNoContent());
        verify(desativarRestauranteUseCase).executar(id);
    }

    @Test
    void deveAtivarRestaurante() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(patch("/restaurantes/{id}/ativar", id)).andExpect(status().isNoContent());
        verify(ativarRestauranteUseCase).executar(id);
    }

    @Test
    void deveRetornar500QuandoOcorrerErroInesperado() throws Exception {
        UUID id = UUID.randomUUID();
        when(buscarRestaurantePorIdUseCase.executar(id)).thenThrow(new RuntimeException("Erro"));

        mockMvc.perform(get("/restaurantes/{id}", id))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Ocorreu um erro inesperado. Contate o suporte técnico."));
    }
}