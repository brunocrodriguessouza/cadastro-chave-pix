package com.itau.cadastrochavepix.controller;

import com.itau.cadastrochavepix.dto.PixDictEntrada;
import com.itau.cadastrochavepix.dto.PixDictSaida;
import com.itau.cadastrochavepix.exception.ValidacaoException;
import com.itau.cadastrochavepix.model.PixDict;
import com.itau.cadastrochavepix.model.TipoChave;
import com.itau.cadastrochavepix.service.PixDictService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/itau")
public class PixDictController {

    private final PixDictService pixDictService;

    public PixDictController(PixDictService pixDictService) {
        this.pixDictService = pixDictService;
    }

    @PostMapping
    public ResponseEntity<PixDictSaida> cadastrarChave(@RequestBody PixDictEntrada pixDictEntrada) {
        try {
            PixDict pixDict = pixDictService.cadastrarChave(pixDictEntrada.converterParaEntidade());
            PixDictSaida pixDictSaida = new PixDictSaida(pixDict);
            return ResponseEntity.status(HttpStatus.CREATED).body(pixDictSaida);
        } catch (Exception e) {
            return ResponseEntity.status(422).build();
        }
    }

    @GetMapping("/consulta/{id}")
    public ResponseEntity<Object> buscarChavePorId(@PathVariable UUID id) {
        Optional<PixDict> pixDictOptional = pixDictService.buscarChavePorId(id);
        if (!pixDictOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chave Não encontrada");
        } else {
            PixDictSaida pixDictSaida = new PixDictSaida(pixDictOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(pixDictSaida);
        }
    }

    @GetMapping("/consulta/tipo-de-chave/{tipoChave}")
    public ResponseEntity<List<PixDictSaida>> buscarChavePorTipo(@PathVariable TipoChave tipoChave) {
        List<PixDict> pixDics = pixDictService.buscarChavePorTipo(tipoChave);
        if (pixDics.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Arrays.asList());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    pixDics.stream()
                            .map(p -> new PixDictSaida(p))
                            .collect(Collectors.toList())
            );
        }
    }
}
