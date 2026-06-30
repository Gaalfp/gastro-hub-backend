package com.techchallenge.gastrohub.infrastructure.api.controller;

import com.techchallenge.gastrohub.application.dto.ItemCardapioRequestDTO;
import com.techchallenge.gastrohub.application.dto.ItemCardapioResponseDTO;
import com.techchallenge.gastrohub.application.usecase.itemcardapio.AtualizarItemCardapioUseCase;
import com.techchallenge.gastrohub.application.usecase.itemcardapio.CriarItemCardapioUseCase;
import com.techchallenge.gastrohub.application.usecase.itemcardapio.InativarItemCardapioUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techchallenge.gastrohub.infrastructure.exception.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemCardapioController.class)
@Import(GlobalExceptionHandler.class)
class ItemCardapioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CriarItemCardapioUseCase criarUseCase;

    @MockBean
    private AtualizarItemCardapioUseCase.BuscarItensPorRestauranteUseCase buscarUseCase;

    @MockBean
    private AtualizarItemCardapioUseCase atualizarUseCase;

    @MockBean
    private InativarItemCardapioUseCase inativarUseCase;

    @Test
    void deveCriarItemCardapio() throws Exception {
        UUID id = UUID.randomUUID();
        UUID restauranteId = UUID.randomUUID();

        ItemCardapioRequestDTO request = new ItemCardapioRequestDTO(
                restauranteId,
                "Pasta Carbonara",
                "Massa com bacon",
                new BigDecimal("45.00"),
                false,
                "/fotos/carbonara.jpg"
        );

        ItemCardapioResponseDTO response = new ItemCardapioResponseDTO(
                id,
                restauranteId,
                "Pasta Carbonara",
                "Massa com bacon",
                new BigDecimal("45.00"),
                false,
                "/fotos/carbonara.jpg",
                true
        );

        when(criarUseCase.executar(any())).thenReturn(response);

        mockMvc.perform(post("/restaurantes/{restauranteId}/itens", restauranteId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Pasta Carbonara"));
    }

    @Test
    void deveListarItensPorRestaurante() throws Exception {
        UUID restauranteId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        ItemCardapioResponseDTO dto = new ItemCardapioResponseDTO(
                itemId,
                restauranteId,
                "Pasta Carbonara",
                "Massa com bacon",
                new BigDecimal("45.00"),
                false,
                "/fotos/carbonara.jpg",
                true
        );

        when(buscarUseCase.executar(restauranteId)).thenReturn(List.of(dto));

        mockMvc.perform(get("/restaurantes/{restauranteId}/itens", restauranteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(itemId.toString()))
                .andExpect(jsonPath("$[0].nome").value("Pasta Carbonara"));
    }

    @Test
    void deveAtualizarItem() throws Exception {
        UUID itemId = UUID.randomUUID();
        UUID restauranteId = UUID.randomUUID();

        ItemCardapioRequestDTO request = new ItemCardapioRequestDTO(
                restauranteId,
                "Pasta à Carbonara",
                "Massa com bacon e queijo",
                new BigDecimal("50.00"),
                false,
                "/fotos/carbonara.jpg"
        );

        ItemCardapioResponseDTO response = new ItemCardapioResponseDTO(
                itemId,
                restauranteId,
                "Pasta à Carbonara",
                "Massa com bacon e queijo",
                new BigDecimal("50.00"),
                false,
                "/fotos/carbonara.jpg",
                true
        );

        when(atualizarUseCase.executar(eq(itemId), any())).thenReturn(response);

        mockMvc.perform(put("/itens/{id}", itemId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pasta à Carbonara"));
    }

    @Test
    void deveInativarItem() throws Exception {
        UUID itemId = UUID.randomUUID();

        mockMvc.perform(delete("/itens/{id}", itemId))
                .andExpect(status().isNoContent());

        verify(inativarUseCase).executar(itemId);
    }

    @Test
    void deveRetornar404AoCriarItemQuandoRestauranteNaoExistir() throws Exception {
        UUID restauranteId = UUID.randomUUID();

        ItemCardapioRequestDTO request = new ItemCardapioRequestDTO(
                restauranteId,
                "Pasta Carbonara",
                "Massa com bacon",
                new BigDecimal("45.00"),
                false,
                "/fotos/carbonara.jpg"
        );

        when(criarUseCase.executar(any()))
                .thenThrow(new EntityNotFoundException("Restaurante não encontrado com o ID informado."));

        mockMvc.perform(post("/restaurantes/{restauranteId}/itens", restauranteId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar400AoAtualizarComJsonInvalido() throws Exception {
        UUID itemId = UUID.randomUUID();

        mockMvc.perform(put("/itens/{id}", itemId)
                        .contentType(APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest());
    }
}
