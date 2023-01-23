package com.itau.cadastrochavepix.repository;

import com.itau.cadastrochavepix.model.Conta;
import com.itau.cadastrochavepix.model.PixDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PixDictRepository extends JpaRepository<PixDict, UUID> {

    Integer countByContaAgenciaAndContaNumero(Integer agencia, Integer numero);

    String findByValorChave(String valorChave);

}
