package com.blaese.error.manager.service.impl;

import com.blaese.error.manager.entity.Log;
import com.blaese.error.manager.repository.LogRepository;
import com.blaese.error.manager.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogRepository repository;

    @Override
    public Log save(Log log) {
        return repository.save(log);
    }

    @Override
    public List<Log> findAllByUserId(Long user) {
        return repository.findByTokenUserIdEquals(user);
    }

    @Override
    public Optional<Log> findByIdAndUserId(Long id, Long userId) {
        return repository.findByIdEqualsAndTokenUserIdEquals(id, userId);
    }

}
