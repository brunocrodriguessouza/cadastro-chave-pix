package com.itau.cadastrochavepix.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itau.cadastrochavepix.exception.ValidacaoException;
import com.itau.cadastrochavepix.model.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PixDictEntrada {

    @JsonProperty(value = "tipo_chave")
    private String tipoChave;

    @JsonProperty(value = "valor_chave")
    private String valorChave;

    @JsonProperty(value = "tipo_conta")
    private String tipoConta;

    @JsonProperty(value = "numero_agencia")
    private Integer numeroAgencia;

    @JsonProperty(value = "numero_conta")
    private Integer numeroConta;

    @JsonProperty(value = "nome_correntista")
    private String nomeCorrentista;

    @JsonProperty(value = "sobrenome_correntista")
    private String sobrenomeCorrentista;


    public PixDict converterParaEntidade() throws ValidacaoException {

        Cliente cliente = Cliente.builder()
                .nomeCorrentista(nomeCorrentista)
                .sobrenomeCorrentista(sobrenomeCorrentista)
                .build();

        Conta conta = Conta.builder()
                .tipoConta(TipoConta.valueOf(tipoConta.toUpperCase().replaceAll("[^\\p{ASCII}]", "")))
                .agencia(numeroAgencia)
                .numero(numeroConta)
                .cliente(cliente)
                .build();

        PixDict pixDict = PixDict.builder()
                .tipoChave(TipoChave.valueOf(tipoChave.toUpperCase().replaceAll("[^\\p{ASCII}]", "")))
                .valorChave(valorChave)
                .conta(conta)
                .build();

        pixDict.getTipoChave().test(pixDict.getValorChave());

        return pixDict;
    }

}
