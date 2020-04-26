package com.blaese.error.manager.repository;

import com.blaese.error.manager.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByTokenEquals(String token);

}
