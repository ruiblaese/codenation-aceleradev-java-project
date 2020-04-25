package com.blaese.error.manager.repository;

import com.blaese.error.manager.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

}
