package com.blaese.error.manager.service;

import com.blaese.error.manager.entity.Token;

import java.util.List;
import java.util.Optional;

public interface TokenService {

    Token save(Token token);

    Optional<Token> findById(Long id);

    List<Token> findAllByUserId(Long user);

    Optional<Token> findByToken(String token);

}
