package com.blaese.error.manager.service.impl;

import com.blaese.error.manager.Application;
import com.blaese.error.manager.entity.Log;
import com.blaese.error.manager.repository.LogRepository;
import com.blaese.error.manager.service.LogService;
import com.blaese.error.manager.util.enums.Environment;
import com.blaese.error.manager.util.enums.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.cert.CertificateNotYetValidException;
import java.util.List;
import java.util.Optional;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogRepository repository;

    @Value("${pagination.items_per_page}")
    private int itemsPerPage;

    @Override
    public Log save(Log log) {
        return repository.save(log);
    }

    @Override
    public Page<Log> findAllByUserId(Long user, int page) {

        PageRequest pageRequest = PageRequest.of(page,itemsPerPage, Sort.by("id"));
        return repository.findByTokenUserIdEquals(user, pageRequest);
    }

    @Override
    public Optional<Log> findByIdAndUserId(Long id, Long userId) {
        return repository.findByIdEqualsAndTokenUserIdEquals(id, userId);
    }

    @Override
    public Page<Log> findByUserIdAndOptionalParams(Long user, String initialData, String finalData, String title, Level level, Environment environment, String token, String sort, int page) {

        String fieldSort = "id";
        PageRequest pageRequest = PageRequest.of(page, itemsPerPage, Sort.by(fieldSort));

        if ((sort != null)&&(sort.indexOf(":") > 0)){
            fieldSort = sort.substring(0,sort.indexOf(":"));
            if (sort.indexOf("desc") > 0){
                pageRequest = PageRequest.of(page, itemsPerPage, Sort.by(fieldSort).descending());
            } else {
                pageRequest = PageRequest.of(page, itemsPerPage, Sort.by(fieldSort));
            }
        }

        int tmpLevel = -1;
        int tmpEnv = -1;

        if (level != null){
            tmpLevel = level.getId();
        }

        if (environment != null){
            System.out.println(environment);
            tmpEnv = environment.getId();
        }

        return repository.findByTokenUserIdEqualsAndOptionalParams(
                user,
                initialData,
                finalData,
                title,
                tmpLevel,
                tmpEnv,
                token,
                pageRequest);
    }

}
