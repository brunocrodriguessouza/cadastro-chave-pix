package com.itau.cadastrochavepix.model;

import com.itau.cadastrochavepix.exception.ValidacaoException;
import lombok.SneakyThrows;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            if(!valida(entrada, REGEX_EMAIL)){
                throw new ValidacaoException("E-mail inválido");
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
    }, ALEATORIA{
        @Override
        public boolean test(String entrada) {
            return true;
        }
    };

    public static final String REGEX_EMAIL = "(.+)@(.+)$";

    boolean valida(String entrada, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(entrada);
        return matcher.matches();
    }


}

