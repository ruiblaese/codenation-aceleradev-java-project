package com.blaese.error.manager.service;

import com.blaese.error.manager.entity.Log;
import com.blaese.error.manager.util.enums.Environment;
import com.blaese.error.manager.util.enums.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LogService {

    Log save(Log log);

    Page<Log> findAllByUserId(Long user, int page);

    Optional<Log> findByIdAndUserId(Long id, Long userId);

    Page<Log> findByUserIdAndOptionalParams(
            Long user,
            String initialData,
            String finalData,
            String title,
            Level level,
            Environment environment,
            String token,
            String sort,
            int page);

}
