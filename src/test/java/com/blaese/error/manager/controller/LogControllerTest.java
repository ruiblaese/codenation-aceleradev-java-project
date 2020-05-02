package com.blaese.error.manager.controller;

import com.blaese.error.manager.dto.LogDTO;
import com.blaese.error.manager.entity.Log;
import com.blaese.error.manager.entity.Token;
import com.blaese.error.manager.service.LogService;
import com.blaese.error.manager.service.TokenService;
import com.blaese.error.manager.util.enums.Environment;
import com.blaese.error.manager.util.enums.Level;
import com.blaese.error.manager.util.enums.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.weaver.patterns.IToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LogControllerTest {

    @MockBean
    LogService service;

    @MockBean
    TokenService tokenService;

    @Autowired
    MockMvc mvc;

    private static final Long ID = 1L;
    private static final Date DATE = new Date();
    private static final Long TOKEN_ID = 1L;
    private static final String TOKEN = "12345678901234567890123456789012";
    private static final String TITLE = "title";
    private static final String DETAILS = "stacktrace";
    private static final Environment ENV = Environment.DEVELOPMENT;
    private static final Level LEVEL = Level.DEBUG;

    private static final Long USER_ID = 1L;

    private static final String POST_URL = "/log";
    private static final String GET_URL = "/log";
    private static final String PUT_URL = "/log/1";
    private static final String GET_URL_BY_ID = "/log/1";

    @Test
    public void testSave() throws Exception {
        BDDMockito.given(service.save(Mockito.any(Log.class)))
                .willReturn(getMockLog());

        BDDMockito.given(tokenService.findByToken(Mockito.anyString()))
                .willReturn(getMockToken());

        mvc.perform(
                MockMvcRequestBuilders
                .post(POST_URL)
                .content(getJsonPayload())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void testFindAllToken() throws Exception{

        List<Log> listToken = new ArrayList<Log>();
        listToken.add(getMockLog());
        listToken.add(getMockLog());

        BDDMockito.given(service.findAllByUserId(USER_ID))
                .willReturn(listToken);

        mvc.perform(
                MockMvcRequestBuilders
                        .get(GET_URL)
                        .requestAttr("loggedUserId", USER_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(ID));
    }

    @Test
    @WithMockUser
    public void testFindById() throws Exception{

        Optional<Log> log = Optional.of(getMockLog());

        BDDMockito.given(service.findByIdAndUserId(ID,USER_ID))
                .willReturn(log);

        mvc.perform(
                MockMvcRequestBuilders
                        .get(GET_URL_BY_ID)
                        .requestAttr("loggedUserId", USER_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.details").isNotEmpty());
    }

    private Optional<Token> getMockToken() {
        Token token = new Token();
        token.setId(1L);
        token.setToken(Util.generateToken());
        token.setActive(true);

        return Optional.of(token);
    }


    private String getJsonPayload() throws JsonProcessingException{

        LogDTO log = new LogDTO();
        log.setId(ID);
        log.setDate(DATE);
        log.setTitle(TITLE);
        log.setDetails(DETAILS);
        log.setEnvironment(ENV);
        log.setLevel(LEVEL);
        log.setToken(TOKEN);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(log);
    }

    private Log getMockLog()  {

        Log log = new Log();
        log.setId(ID);
        log.setDate(DATE);
        log.setTitle(TITLE);
        log.setDetails(DETAILS);
        log.setEnvironment(ENV);
        log.setLevel(LEVEL);

        Token token = new Token();
        token.setId(TOKEN_ID);
        log.setToken(token);

        return log;
    }

}
