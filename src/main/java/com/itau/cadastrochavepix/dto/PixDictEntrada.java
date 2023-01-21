package com.itau.cadastrochavepix.dto;

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



    public PixDict converterParaEntidade(){
        Conta conta = Conta.builder()
                .tipoConta(TipoConta.valueOf(tipoConta.toUpperCase()))
                .agencia(numeroAgencia)
                .numero(numeroConta)
                .build();

        Cliente cliente = Cliente.builder()
                .nomeCorrentista(nomeCorrentista)
                .sobrenomeCorrentista(sobrenomeCorrentista)
                .build();

        PixDict pixDict = PixDict.builder()
                .tipoChave(TipoChave.valueOf(tipoChave.toUpperCase()))
                .valorChave(valorChave)
                .conta(conta)
                .cliente(cliente)
                .build();

        return pixDict;
    }

}



//    TIPO CHAVE (celular|email|cpf|cnpj|aleatorio) Texto (9) SIM
//        VALOR CHAVE Texto (77) SIM
//        TIPO CONTA (corrente|poupança) Texto (10) SIM
//        NUMERO AGENCIA Numérico (4) SIM
//        NUMERO CONTA Numérico (8) SIM
//        NOME CORRENTISTA Texto (30) SIM
//        SOBRENOME CORRENTISTA
