package com.itau.cadastrochavepix.dto;

import com.itau.cadastrochavepix.exception.ValidacaoException;
import com.itau.cadastrochavepix.model.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PixDictEntrada {

    private String tipoChave;
    private String valorChave;
    private String tipoConta;
    private Integer numeroAgencia;
    private Integer numeroConta;
    private String nomeCorrentista;
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
