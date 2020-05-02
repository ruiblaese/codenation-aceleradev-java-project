package com.blaese.error.manager.controller;

import com.blaese.error.manager.dto.TokenDTO;
import com.blaese.error.manager.entity.Token;
import com.blaese.error.manager.entity.User;
import com.blaese.error.manager.response.Response;
import com.blaese.error.manager.service.TokenService;
import com.blaese.error.manager.service.UserService;
import com.blaese.error.manager.util.enums.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("token")
@Api(tags = {"Token"})
@ApiOperation(value = "Token")
public class TokenController {

    @Autowired
    private TokenService service;

    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation(value = "Cadastra Token utilizado na aplicação monitorada", response = TokenDTO.class)
    public ResponseEntity<Response<TokenDTO>> create(@Valid @RequestBody TokenDTO dto,
                                                     @ApiIgnore
                                                     @RequestAttribute String loggedUserId,
                                                     BindingResult result) {

        Response<TokenDTO> response = new Response<TokenDTO>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (!dto.getUser().equals(Long.valueOf(loggedUserId))){
            response.getErrors().add("Usuário do Token diferente do usuário logado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if ((dto.getToken() != null)&&(!dto.getToken().isEmpty())){

            Optional<Token> tokenExistent = service.findByToken(dto.getToken());

            if (tokenExistent.isPresent()) {
                response.getErrors().add("Token já cadastro, envie nulo para gerar um novo token aleatorio");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }
        dto.setToken(getNewTokenNoExitent());

        if (dto.getActive() == null){
            dto.setActive(true);
        }

        Token token = service.save(convertDtoToEntity(dto));

        response.setData(this.convertEntityToDto(token));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private String getNewTokenNoExitent() {
        String newToken = Util.generateToken();
        Optional<Token> tokenExiToken = service.findByToken(newToken);
        while (tokenExiToken.isPresent()){
            newToken = Util.generateToken();
            tokenExiToken = service.findByToken(newToken);
        }
        return newToken;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza dados do Token", response = TokenDTO.class)
    public ResponseEntity<Response<TokenDTO>> update(@Valid @RequestBody TokenDTO dto,
                                                     @PathVariable("id") Long id,
                                                     @ApiIgnore
                                                     @RequestAttribute String loggedUserId,
                                                     BindingResult result) {
        Response<TokenDTO> response = new Response<TokenDTO>();

        Optional<Token> tokenForUpdate = service.findById(id);

        if (!tokenForUpdate.isPresent()){
            response.getErrors().add("Token Id não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
        }

        if (dto.getId() == null){
            dto.setId(id);
        } else {
            if (!dto.getId().equals(id)){
                response.getErrors().add("Não é permitido alterar o Id");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }

        if (!dto.getUser().equals(Long.valueOf(loggedUserId))){
            response.getErrors().add("Usuário do Token diferente do usuário logado.");
        }

        if (!tokenForUpdate.get().getToken().equals(dto.getToken())){
            response.getErrors().add("Token/Chave não pode ser alterado");
        }

        if (response.getErrors().size() > 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Token token = service.save(convertDtoToEntity(dto));

        response.setData(this.convertEntityToDto(token));

        return ResponseEntity.ok().body(response);
    }

    @GetMapping()
    @ApiOperation(
            value = "Retorna todos os token do usuário (token utilizados nas aplicações monitoradas)"
    )
    public ResponseEntity<Response<List<TokenDTO>>> findAllByUser(@ApiIgnore @RequestAttribute String loggedUserId) {

        ArrayList<Token> list = (ArrayList<Token>) service.findAllByUserId(Long.valueOf(loggedUserId));

        List<TokenDTO> listDTO = new ArrayList<>();
        list.forEach(entity -> listDTO.add(convertEntityToDto(entity)));

        Response<List<TokenDTO>> response = new Response<List<TokenDTO>>();
        response.setData(listDTO);

        return ResponseEntity.ok().body(response);
    }

    private Token convertDtoToEntity(TokenDTO dto) {
        User user = new User();
        user.setId(dto.getUser());

        Token entity = new Token();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setToken(dto.getToken());
        entity.setActive(dto.getActive());
        entity.setUser(user);

        if (dto.getParent() != null) {
            Token parentToken = new Token();
            parentToken.setId((dto.getParent()));

            entity.setParent(parentToken);
        }

        return entity;
    }

    private TokenDTO convertEntityToDto(Token entity) {
        TokenDTO dto = new TokenDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setToken(entity.getToken());
        dto.setActive(entity.getActive());
        dto.setUser(entity.getUser().getId());
        if (entity.getParent() != null) {
            dto.setParent(entity.getParent().getId());
        }
        return dto;
    }

}
