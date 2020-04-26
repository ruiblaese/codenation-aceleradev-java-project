package com.blaese.error.manager.service.impl;

import com.blaese.error.manager.entity.Log;
import com.blaese.error.manager.repository.LogRepository;
import com.blaese.error.manager.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogRepository repository;

    @Override
    public Log save(Log log) {
        return repository.save(log);
    }

}
