package com.itau.cadastrochavepix.model;

import com.itau.cadastrochavepix.exception.ValidacaoException;

public interface Validacao {

    void validar(String entrada) throws ValidacaoException;
}
