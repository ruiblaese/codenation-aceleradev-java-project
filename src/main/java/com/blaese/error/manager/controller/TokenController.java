package com.blaese.error.manager.controller;

import com.blaese.error.manager.dto.TokenDTO;
import com.blaese.error.manager.dto.UserDTO;
import com.blaese.error.manager.entity.Token;
import com.blaese.error.manager.entity.User;
import com.blaese.error.manager.response.Response;
import com.blaese.error.manager.service.TokenService;
import com.blaese.error.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("token")
public class TokenController {

    @Autowired
    private TokenService service;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Response<TokenDTO>> create(@Valid @RequestBody TokenDTO dto, BindingResult result){

        Response<TokenDTO> response = new Response<TokenDTO>();

        if (result.hasErrors()){
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Token token = service.save(convertDtoToEntity(dto));

        response.setData(this.convertEntityToDto(token));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private Token convertDtoToEntity(TokenDTO dto){
        User user = new User();
        user.setId(dto.getUser());


        Token entity = new Token();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setToken(dto.getToken());
        entity.setActive(dto.getActive());
        entity.setUser(user);

        if (dto.getParent() != null){
            Token parentToken = new Token();
            parentToken.setId((dto.getParent()));
            entity.setParent(parentToken);
        }

        return entity;
    }

    private TokenDTO convertEntityToDto(Token user){
        TokenDTO dto = new TokenDTO();
        dto.setId(user.getId());
        dto.setDescription(user.getDescription());
        dto.setToken(user.getToken());
        dto.setActive(user.getActive());
        dto.setUser(user.getUser().getId());
        if (user.getParent() != null){
            dto.setParent(user.getParent().getId());
        }
        return dto;
    }

}
