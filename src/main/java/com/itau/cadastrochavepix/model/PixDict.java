package com.itau.cadastrochavepix.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PixDict {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private TipoChave tipoChave;

    @Column(nullable = false)
    private String valorChave;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Conta conta;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cliente cliente;


    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime dataHoraInclusao;

}
