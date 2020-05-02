package com.blaese.error.manager.service.impl;

import com.blaese.error.manager.entity.Token;
import com.blaese.error.manager.repository.TokenRepository;
import com.blaese.error.manager.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    TokenRepository repository;

    @Override
    public Token save(Token token) {
        return repository.save(token);
    }

    @Override
    public Optional<Token> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Token> findAllByUserId(Long user) {
        return repository.findByUserIdEquals(user);
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return repository.findByTokenEquals(token);
    }
}
