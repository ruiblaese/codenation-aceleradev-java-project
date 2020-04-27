package com.blaese.error.manager.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class TokenDTO {

    private Long id;

    private Long user;

    private Long parent;

    @Length(min = 3, max=50, message = "O nome deve conter entre 3 e 100 caracteres")
    private String description;

    private String token;

    @NotNull
    private Boolean active;

}
