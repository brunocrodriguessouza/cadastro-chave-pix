package com.itau.cadastrochavepix.service;

import com.itau.cadastrochavepix.dto.PixDictSaida;
import com.itau.cadastrochavepix.exception.ValidacaoException;
import com.itau.cadastrochavepix.model.*;
import com.itau.cadastrochavepix.repository.PixDictRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PixDictService {

    public static final int QUANTIDADE_PESSOA_FISICA = 5;
    public static final int QUANTIDADE_PESSOA_JURIDICA = 20;
    private final ContaService contaService;
    private final PixDictRepository repository;

    public PixDictService(ContaService contaService, PixDictRepository repository) {
        this.contaService = contaService;
        this.repository = repository;
    }

    private void verificarTipoCliente(PixDict pixDict) {
        if (pixDict.getTipoChave().equals(TipoChave.CPF)) {
            pixDict.getConta().getCliente().setTipoCliente(TipoCliente.FISICA);
        }

        if (pixDict.getTipoChave().equals(TipoChave.CNPJ)) {
            pixDict.getConta().getCliente().setTipoCliente(TipoCliente.JURIDICA);
        }
    }

    public PixDict cadastrarChave(PixDict pixDict) throws Exception {
        Conta conta = pixDict.getConta();

        Optional<Conta> contaOptional = contaService.buscarPorAgenciaEConta(conta.getAgencia(), conta.getNumero());

        if (contaOptional.isPresent()) {
            pixDict.setConta(contaOptional.get());
        }

        verificarTipoCliente(pixDict);

        int quantidadeDeChaves = repository.countByContaAgenciaAndContaNumero(conta.getAgencia(), conta.getNumero());

        Cliente cliente = pixDict.getConta().getCliente();
        if ((TipoCliente.FISICA.equals(cliente.getTipoCliente())
                && quantidadeDeChaves >= QUANTIDADE_PESSOA_FISICA)
                || quantidadeDeChaves >= QUANTIDADE_PESSOA_JURIDICA) {
            throw new ValidacaoException("Excedeu o numero maximo de chaves");
        }

        return repository.save(pixDict);
    }

    public Optional<PixDict> buscarChavePorId(UUID id) {
        return repository.findById(id);
    }

    public List<PixDict> buscarChavePorTipo(TipoChave tipoChave) {
        return repository.findAllByTipoChave(tipoChave);
    }
}
