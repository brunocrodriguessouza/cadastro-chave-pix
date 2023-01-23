package com.itau.cadastrochavepix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private Integer agencia;

    @Column(nullable = false)
    private TipoConta tipoConta;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cliente cliente;

}
