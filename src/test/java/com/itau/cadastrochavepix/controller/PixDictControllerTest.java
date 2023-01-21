package com.itau.cadastrochavepix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.cadastrochavepix.CadastroChavePixApplication;
import com.itau.cadastrochavepix.dto.PixDictEntrada;
import com.itau.cadastrochavepix.repository.PixDictRepository;
import com.itau.cadastrochavepix.service.PixDictService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {CadastroChavePixApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PixDictControllerTest {
    @Autowired
    MockMvc mockMvc;

    @SpyBean
    PixDictService service;

    @SpyBean
    PixDictRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void deveriaCadastrarChave() throws Exception {

        PixDictEntrada dto = PixDictEntrada.builder()
                .nomeCorrentista("Bruno")
                .sobrenomeCorrentista("Souza")
                .numeroAgencia(5846)
                .numeroConta(5843)
                .tipoChave("cpf")
                .tipoConta("corrente")
                .valorChave("79924466071")
                .build();

        ResultActions resultActions = mockMvc.perform(
                post("/itau/pix")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoChave").value("cpf"))
                .andExpect(jsonPath("$.valorChave").value("79924466071"))
                .andExpect(jsonPath("$.tipoConta").value("corrente"))
                .andExpect(jsonPath("$.numeroAgencia").value(5846))
                .andExpect(jsonPath("$.numeroConta").value(5843))
                .andExpect(jsonPath("$.nomeCorrentista").value("Bruno"))
                .andExpect(jsonPath("$.sobrenomeCorrentista").value("Souza"));
    }

}