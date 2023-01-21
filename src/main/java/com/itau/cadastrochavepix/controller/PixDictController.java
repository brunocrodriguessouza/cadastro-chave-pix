package com.itau.cadastrochavepix.controller;

import com.itau.cadastrochavepix.dto.PixDictEntrada;
import com.itau.cadastrochavepix.dto.PixDictSaida;
import com.itau.cadastrochavepix.model.PixDict;
import com.itau.cadastrochavepix.service.PixDictService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itau/pix")
public class PixDictController {

    private final PixDictService pixDictService;

    public PixDictController(PixDictService pixDictService) {
        this.pixDictService = pixDictService;
    }

    @PostMapping
    public ResponseEntity<PixDictSaida> cadastrarChave(@RequestBody PixDictEntrada pixDictEntrada){
        PixDict pixDict = pixDictService.cadastrarChave(pixDictEntrada.converterParaEntidade());
        PixDictSaida pixDictSaida = new PixDictSaida(pixDict);
        return ResponseEntity.status(HttpStatus.CREATED).body(pixDictSaida);
    }
}
