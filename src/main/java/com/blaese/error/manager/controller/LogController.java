package com.blaese.error.manager.controller;

import com.blaese.error.manager.dto.LogDTO;
import com.blaese.error.manager.dto.TokenDTO;
import com.blaese.error.manager.entity.Log;
import com.blaese.error.manager.entity.Token;
import com.blaese.error.manager.entity.User;
import com.blaese.error.manager.response.Response;
import com.blaese.error.manager.service.LogService;
import com.blaese.error.manager.service.TokenService;
import com.blaese.error.manager.util.enums.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Response<List<LogDTO>>> findAllByUser(@ApiIgnore @RequestAttribute String loggedUserId) {

        ArrayList<Log> list = (ArrayList<Log>) service.findAllByUserId(Long.valueOf(loggedUserId));

        List<LogDTO> listDTO = new ArrayList<>();
        list.forEach(entity -> listDTO.add(convertEntityToDto(entity)));

        Response<List<LogDTO>> response = new Response<List<LogDTO>>();
        response.setData(listDTO);

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
