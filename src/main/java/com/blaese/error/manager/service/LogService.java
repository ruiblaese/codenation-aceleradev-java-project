package com.blaese.error.manager.service;

import com.blaese.error.manager.entity.Log;

import java.util.List;
import java.util.Optional;

public interface LogService {

    Log save(Log log);

    List<Log> findAllByUserId(Long user);

    Optional<Log> findByIdAndUserId(Long id, Long userId);

}
