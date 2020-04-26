package com.blaese.error.manager.service;

import com.blaese.error.manager.entity.Token;

import java.util.Optional;

public interface TokenService {

    Token save(Token token);

    Optional<Token> findByToken(String token);

}
