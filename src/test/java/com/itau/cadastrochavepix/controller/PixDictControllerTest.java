package com.itau.cadastrochavepix.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.cadastrochavepix.CadastroChavePixApplication;
import com.itau.cadastrochavepix.dto.PixDictEntrada;
import com.itau.cadastrochavepix.repository.ContaRepository;
import com.itau.cadastrochavepix.repository.PixDictRepository;
import com.itau.cadastrochavepix.service.ContaService;
import com.itau.cadastrochavepix.service.PixDictService;
import org.junit.jupiter.api.DisplayName;
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
    PixDictRepository repository;

    @SpyBean
    ContaService contaService;

    @SpyBean
    PixDictService service;

    @SpyBean
    ContaRepository contaRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Teste de Inclusão de chave com e-mail válido")
    public void deveriaCadastrarChaveEmailComSucesso() throws Exception {

        PixDictEntrada dto = PixDictEntrada.builder()
                .nomeCorrentista("Bruno")
                .sobrenomeCorrentista("Souza")
                .numeroAgencia(5846)
                .numeroConta(1001)
                .tipoChave("email")
                .valorChave("bruno1@itau.com.br")
                .tipoConta("corrente")
                .build();

        ResultActions resultActions = performPost(dto);

        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipo_chave").value("email"))
                .andExpect(jsonPath("$.valor_chave").value("bruno1@itau.com.br"))
                .andExpect(jsonPath("$.tipo_conta").value("corrente"))
                .andExpect(jsonPath("$.numero_agencia").value(5846))
                .andExpect(jsonPath("$.numero_conta").value(1001))
                .andExpect(jsonPath("$.nome_correntista").value("Bruno"))
                .andExpect(jsonPath("$.sobrenome_correntista").value("Souza"));
    }

    @Test
    @DisplayName("Teste de Inclusão de chave com e-mail inválido")
    public void deveriaLancarExcecaoComEmailInvalido() throws Exception {

        PixDictEntrada dto = PixDictEntrada.builder()
                .nomeCorrentista("Bruno")
                .sobrenomeCorrentista("Souza")
                .numeroAgencia(5846)
                .numeroConta(1002)
                .tipoChave("email")
                .valorChave("bruno2.com.br")
                .tipoConta("corrente")

                .build();

        ResultActions resultActions = performPost(dto);

        resultActions
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Teste de Inclusão de chave duplicada")
    public void deveriaLancarExcecaoChaveDuplicada() throws Exception {

        PixDictEntrada dto = PixDictEntrada.builder()
                .nomeCorrentista("Bruno")
                .sobrenomeCorrentista("Souza")
                .numeroAgencia(5846)
                .numeroConta(1003)
                .tipoChave("email")
                .valorChave("bruno3.souza@itau.com.br")
                .tipoConta("corrente")
                .build();

        performPost(dto);

        ResultActions resultActions = performPost(dto);

        resultActions
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Teste com mais de 5 chaves Pessoa Física")
    public void deveriaLancarExcecaoComMaisDe5ChavesPessoaFisica() throws Exception {

        PixDictEntrada dto = PixDictEntrada.builder()
                .nomeCorrentista("Bruno")
                .sobrenomeCorrentista("Souza")
                .numeroAgencia(5846)
                .numeroConta(1004)
                .tipoChave("cpf")
                .valorChave("12345678900")
                .tipoConta("corrente")
                .build();

        ResultActions resultActions = performPost(dto);

        resultActions
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        dto.setTipoChave("aleatoria");

        for(int i=0; i<4; i++ ){
            dto.setValorChave("chavealetoria"+i);
            resultActions = performPost(dto);
            resultActions
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful());
        }

        resultActions = performPost(dto);

        resultActions
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    private ResultActions performPost(PixDictEntrada dto) throws Exception {
        return mockMvc.perform(
                post("/itau")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON)
        );
    }


}