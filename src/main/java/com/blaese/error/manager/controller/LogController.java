package com.blaese.error.manager.controller;

import com.blaese.error.manager.dto.LogDTO;
import com.blaese.error.manager.dto.TokenDTO;
import com.blaese.error.manager.entity.Log;
import com.blaese.error.manager.entity.Token;
import com.blaese.error.manager.entity.User;
import com.blaese.error.manager.response.Response;
import com.blaese.error.manager.service.LogService;
import com.blaese.error.manager.service.TokenService;
import com.blaese.error.manager.util.enums.Environment;
import com.blaese.error.manager.util.enums.Level;
import com.blaese.error.manager.util.enums.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("log")
@Api(value = "Log",  tags = { "Log" })
public class LogController {

    @Autowired
    private LogService service;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @ApiOperation(value = "Cadastra Log", response = LogDTO.class)
    public ResponseEntity<Response<LogDTO>> create(@Valid @RequestBody LogDTO dto,
                                                     BindingResult result) {

        Response<LogDTO> response = new Response<LogDTO>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<Token> token = tokenService.findByToken(dto.getToken());

        if (!token.isPresent()){
            response.getErrors().add("Token de cadastro não encontrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (!token.get().getActive()){
            response.getErrors().add("Token desativado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Log log = service.save(convertDtoToEntity(dto));

        response.setData(this.convertEntityToDto(log));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping()
    @ApiOperation(
            value = "Retorna todos os logs do usuário"
    )
    public ResponseEntity<Response<Page<LogDTO>>> findAllByUser(@ApiIgnore @RequestAttribute String loggedUserId,
                                                                @ApiParam(name = "Data Inicial") @RequestParam("initialData") Optional<String> initialData,
                                                                @ApiParam(name = "Data Final") @RequestParam("finalData") Optional<String> finalData,
                                                                @ApiParam(name = "Título") @RequestParam("title") Optional<String> title,
                                                                @ApiParam(name = "Level", allowableValues = "ERROR,WARNING,DEBUG") @RequestParam("level") Optional<Level> level,
                                                                @ApiParam(name = "Environment", allowableValues = "HOMOLOGATION,PRODUCTION,DEVELOPMENT") @RequestParam("environment") Optional<Environment> environment,
                                                                @ApiParam(name = "Token") @RequestParam("token") Optional<String> token,
                                                                @ApiParam(name = "Ordenação", allowableValues = "date:asc,date:desc,title:asc,title:desc,level,environment,token") @RequestParam("sort") Optional<String> sort,
                                                                @ApiParam(name = "Pagina")@RequestParam(name = "page", defaultValue = "0") int page
                                                                ) {


        Page<Log> pageLog = service.findByUserIdAndOptionalParams(
                Long.valueOf(loggedUserId),
                initialData.orElse(null),
                finalData.orElse(null),
                title.orElse(null),
                level.orElse(null),
                environment.orElse(null),
                token.orElse(null),
                sort.orElse(null),
                page
                );


        // Page<Log> pageLog = service.findAllByUserId(Long.valueOf(loggedUserId), page);

        Page<LogDTO> pageLogDTO = pageLog.map(i -> this.convertEntityToDto(i));

        Response<Page<LogDTO>> response = new Response<Page<LogDTO>>();
        response.setData(pageLogDTO);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    @ApiOperation(
            value = "Retorna log completo"
    )
    public ResponseEntity<Response<LogDTO>> findByIDAndUser(@PathVariable("id") Long id,
                                                            @ApiIgnore @RequestAttribute String loggedUserId) {

        Response<LogDTO> response = new Response<LogDTO>();

        Optional<Log> log =  service.findByIdAndUserId(id, Long.valueOf(loggedUserId));

        if (!log.isPresent()){
            response.getErrors().add("Log não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        LogDTO dto = new LogDTO();
        dto.setId(log.get().getId());
        dto.setDate(log.get().getDate());
        dto.setTitle(log.get().getTitle());
        dto.setDetails(log.get().getDetails());
        dto.setLevel(log.get().getLevel());
        dto.setEnvironment(log.get().getEnvironment());
        dto.setToken(log.get().getToken().getToken());

        response.setData(dto);

        return ResponseEntity.ok().body(response);
    }

    private Log convertDtoToEntity(LogDTO dto) {

        Optional<Token> token = tokenService.findByToken(dto.getToken());

        Log entity = new Log();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setTitle(dto.getTitle());
        entity.setDetails(dto.getDetails());
        entity.setLevel(dto.getLevel());
        entity.setEnvironment(dto.getEnvironment());

        if (token.isPresent()) {
            entity.setToken(token.get());
        }
        return entity;
    }

    private LogDTO convertEntityToDto(Log entity) {
        LogDTO dto = new LogDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setTitle(entity.getTitle());
        // dto.setDetails(entity.getDetails());
        dto.setLevel(entity.getLevel());
        dto.setEnvironment(entity.getEnvironment());
        dto.setToken(entity.getToken().getToken());

        return dto;
    }
}
