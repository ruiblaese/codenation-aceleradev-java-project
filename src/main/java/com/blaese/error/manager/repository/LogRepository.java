package com.blaese.error.manager.repository;

import com.blaese.error.manager.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findByTokenUserIdEquals(Long user);

}
