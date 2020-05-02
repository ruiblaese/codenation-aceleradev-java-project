package com.blaese.error.manager.repository;

import com.blaese.error.manager.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findByTokenUserIdEquals(Long user);

    Optional<Log> findByIdEqualsAndTokenUserIdEquals(Long id, Long userId);

}
