package com.blaese.error.manager.service;

import com.blaese.error.manager.entity.Log;

import java.util.List;

public interface LogService {

    Log save(Log log);

    List<Log> findAllByUserId(Long user);

}
