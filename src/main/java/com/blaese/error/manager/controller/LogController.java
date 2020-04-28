package com.blaese.error.manager.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("log")
@Api(value = "Log",  tags = { "Log" })
public class LogController {

}
