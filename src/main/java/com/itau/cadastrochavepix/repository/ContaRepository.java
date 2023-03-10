package com.itau.cadastrochavepix.repository;

import com.itau.cadastrochavepix.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContaRepository extends JpaRepository<Conta, UUID> {

    Optional<Conta> findByAgenciaAndNumero(Integer agencia, Integer conta);
}
