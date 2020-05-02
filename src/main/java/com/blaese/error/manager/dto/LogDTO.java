package com.blaese.error.manager.dto;

import com.blaese.error.manager.util.enums.Environment;
import com.blaese.error.manager.util.enums.Level;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class LogDTO {

    private Long id;

    @NotNull
    private Date date;

    @NotNull
    private String title;

    @NotNull
    private String details;

    @NotNull
    private Environment environment;

    @NotNull
    private Level level;

    @NotNull
    private String token;
}
