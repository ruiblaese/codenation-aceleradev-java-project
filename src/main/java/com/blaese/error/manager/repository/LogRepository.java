package com.blaese.error.manager.repository;

import com.blaese.error.manager.entity.Log;
import com.blaese.error.manager.util.enums.Environment;
import com.blaese.error.manager.util.enums.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long> {

    Page<Log> findByTokenUserIdEquals(Long user, Pageable pageable);

    Optional<Log> findByIdEqualsAndTokenUserIdEquals(Long id, Long userId);

    @Query(value = "select\n" +
            "\tlog.*\n" +
            "from log log\n" +
            "inner join token on token.id = log.token " +
            "where " +
            " (token.users = :user) and "+
            " ((cast(:initialData as varchar) is null) or (log.date >= cast(cast(:initialData as varchar) as timestamp))) and "+
            " ((cast(:finalData as varchar) is null) or (log.date <= cast(cast(:finalData as varchar) as timestamp))) and "+
            " ((cast(:title as varchar) is null) or (log.title = cast(:title as varchar))) and "+
            " ((cast(:environment as integer) = -1) or (log.environment = cast(:environment as integer)) ) and "+
            " ((cast(:level as integer) = -1) or (log.level = cast(:level as integer)) ) and "+
            " ((cast(:token as varchar) is null) or (token.token = cast(:token as varchar)))" ,

            countQuery = "select\n" +
                    "\tcount(1)\n" +
                    "from log\n" +
                    "inner join token on token.id = log.token " +
                    "where " +
                    " (token.users = :user) and "+
                    " (cast(:initialData as varchar) is null) and "+
                    " (cast(:finalData as varchar) is null) and "+
                    " ((cast(:title as varchar) is null) or (log.title = cast(:title as varchar))) and "+
                    " ((cast(:environment as integer) != -1) or (log.environment = cast(:environment as integer)) ) and "+
                    " ((cast(:level as integer) != -1) or (log.level = cast(:level as integer)) ) and "+
                    " ((cast(:token as varchar) is null) or (token.token = cast(:token as varchar)))" ,
            nativeQuery = true)
    Page<Log> findByTokenUserIdEqualsAndOptionalParams(
            @Param("user") Long user,
            @Param("initialData") String initialData,
            @Param("finalData") String finalData,
            @Param("title") String title,
            @Param("level") int level,
            @Param("environment") int environment,
            @Param("token") String token,
            Pageable pageable);
}
