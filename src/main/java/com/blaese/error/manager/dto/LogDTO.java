package com.blaese.error.manager.dto;

import com.blaese.error.manager.util.enums.Environment;
import com.blaese.error.manager.util.enums.Level;
import lombok.Data;

import java.util.Date;

@Data
public class LogDTO {

    private Long id;

    private Date date;

    private String title;

    private String details;

    private Environment environment;

    private Level level;

    private Long token;
}
