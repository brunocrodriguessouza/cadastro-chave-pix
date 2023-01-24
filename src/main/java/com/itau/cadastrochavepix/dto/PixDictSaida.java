package com.itau.cadastrochavepix.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itau.cadastrochavepix.model.Cliente;
import com.itau.cadastrochavepix.model.Conta;
import com.itau.cadastrochavepix.model.PixDict;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PixDictSaida {

    private UUID id;

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

    @JsonProperty(value = "data_hora_inclusao")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime datahoraInclusao;

    public PixDictSaida(PixDict pixDict){
        Conta conta = pixDict.getConta();
        Cliente cliente = conta.getCliente();

        this.id = pixDict.getId();
        this.tipoChave =pixDict.getTipoChave().name().toLowerCase();
        this.valorChave = pixDict.getValorChave();
        this.tipoConta = conta.getTipoConta().name().toLowerCase();
        this.numeroAgencia = conta.getAgencia();
        this.numeroConta = conta.getNumero();
        this.nomeCorrentista = cliente.getNomeCorrentista();
        this.sobrenomeCorrentista = (cliente.getSobrenomeCorrentista() != null ? cliente.getSobrenomeCorrentista() : "");
        this.datahoraInclusao = LocalDateTime.now();
    }

}

