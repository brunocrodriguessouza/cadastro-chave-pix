package com.itau.cadastrochavepix.model;

import com.itau.cadastrochavepix.exception.ValidacaoException;
import lombok.SneakyThrows;

import java.util.function.Predicate;

public enum TipoChave implements Predicate<String>{

    CELULAR{
        @Override
        public boolean test(String entrada) {
            return true;
        }
    }, EMAIL{
        @SneakyThrows
        @Override
        public boolean test(String entrada){
            if(!entrada.contains("@")){
                throw new ValidacaoException("Errrou");
            }
            return true;
        }
    }, CPF{
        @Override
        public boolean test(String entrada) {
            return true;
        }
    }, CNPJ{
        @Override
        public boolean test(String entrada) {
            return true;
        }
    }, ALEATORIO{
        @Override
        public boolean test(String entrada) {
            return true;
        }
    };


}
