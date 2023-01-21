package com.itau.cadastrochavepix.service;

import com.itau.cadastrochavepix.model.PixDict;
import com.itau.cadastrochavepix.repository.PixDictRepository;
import org.springframework.stereotype.Service;

@Service
public class PixDictService {

    private final PixDictRepository repository;

    public PixDictService(PixDictRepository repository) {
        this.repository = repository;
    }

    public PixDict cadastrarChave(PixDict pixDict) {
        return repository.save(pixDict);
    }
}
