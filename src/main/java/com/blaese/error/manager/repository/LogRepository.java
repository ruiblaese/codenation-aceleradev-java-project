package com.blaese.error.manager.repository;

import com.blaese.error.manager.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
