package com.blaese.error.manager.controller;

import com.blaese.error.manager.dto.TokenDTO;
import com.blaese.error.manager.dto.UserDTO;
import com.blaese.error.manager.entity.User;
import com.blaese.error.manager.response.Response;
import com.blaese.error.manager.security.utils.Bcrypt;
import com.blaese.error.manager.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
@Api(value = "Usuário",  tags = { "Usuário" })
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    @ApiOperation(value = "Cadastra Usuário", response = TokenDTO.class)
    public ResponseEntity<Response<UserDTO>> create(@Valid @RequestBody UserDTO dto, BindingResult result){

        Response<UserDTO> response = new Response<UserDTO>();

        if (result.hasErrors()){
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        User user = service.save(convertDtoToEntity(dto));

        response.setData(this.convertEntityToDto(user));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    @ApiOperation(value = "Retorna todos os Usuário (rota apenas de testes, remover ou adicionar regra permissao para ADM do sistema", response = TokenDTO.class)
    public ResponseEntity<Response<List<UserDTO>>> findAll(){

        ArrayList<User> list = (ArrayList<User>) service.findAll();

        List<UserDTO> dto = new ArrayList<>();
        list.forEach(i -> dto.add(convertEntityToDto(i)));

        Response<List<UserDTO>> response = new Response<List<UserDTO>>();
        response.setData(dto);

        return ResponseEntity.ok().body(response);

    }

    private User convertDtoToEntity(UserDTO dto){
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPassword(Bcrypt.getHash(dto.getPassword()));

        return user;
    }

    private UserDTO convertEntityToDto(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        // dto.setPassword(user.getPassword()); // Comentando para nao devolver a senha criptograda

        return dto;
    }


}
