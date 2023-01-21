package com.itau.cadastrochavepix.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itau.cadastrochavepix.model.Cliente;
import com.itau.cadastrochavepix.model.Conta;
import com.itau.cadastrochavepix.model.PixDict;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PixDictSaida {

    private UUID id;
    private String tipoChave;
    private String valorChave;
    private String tipoConta;
    private Integer numeroAgencia;
    private Integer numeroConta;
    private String nomeCorrentista;
    private String sobrenomeCorrentista;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime datahoraInclusao;

    public PixDictSaida(PixDict pixDict){
        Cliente cliente = pixDict.getCliente();
        Conta conta = pixDict.getConta();

        this.id = pixDict.getId();
        this.tipoChave =pixDict.getTipoChave().name().toLowerCase();
        this.valorChave = pixDict.getValorChave();
        this.tipoConta = conta.getTipoConta().name().toLowerCase();
        this.numeroAgencia = conta.getAgencia();
        this.numeroConta = conta.getNumero();
        this.nomeCorrentista = cliente.getNomeCorrentista();
        this.sobrenomeCorrentista =cliente.getSobrenomeCorrentista();
        this.datahoraInclusao = pixDict.getDataHoraInclusao();
    }

}

