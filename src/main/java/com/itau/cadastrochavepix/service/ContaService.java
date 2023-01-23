package com.itau.cadastrochavepix.service;

import com.itau.cadastrochavepix.model.Conta;
import com.itau.cadastrochavepix.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaService {

    private final ContaRepository repository;

    public ContaService(ContaRepository repository) {
        this.repository = repository;
    }

    Optional<Conta> buscarPorAgenciaEConta(Integer agencia, Integer conta){
        return repository.findByAgenciaAndNumero(agencia, conta);
    }
}
