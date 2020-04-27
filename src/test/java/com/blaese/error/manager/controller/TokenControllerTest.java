package com.blaese.error.manager.controller;

import com.blaese.error.manager.dto.TokenDTO;
import com.blaese.error.manager.entity.Token;
import com.blaese.error.manager.service.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TokenControllerTest {

    @MockBean
    TokenService service;

    @Autowired
    MockMvc mvc;

    private static final Long ID = 1L;
    private static final Long USER_ID = 1L;
    private static final String DESCRIPTION = "description";
    private static final Boolean ACTIVE = true;
    private static final Long PARENT_ID = 1L;
    private static final String TOKEN = "12345678901234567890123456789012";

    private static final String URL = "/token";

    @Test
    public void testSave() throws Exception{

        BDDMockito.given(service.save(Mockito.any(Token.class)))
                .willReturn(getMockToken());

        mvc.perform(
                MockMvcRequestBuilders
                        .post(URL)
                        .content(getJsonPayload())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    private Token getMockToken(){
        Token token = new Token();
        token.setId(ID);
        //token.setUser(USER_ID);
        token.setDescription(DESCRIPTION);
        token.setActive(ACTIVE);
        token.setToken(TOKEN);
        //token.setParent(PARENT_ID);

        return token;
    }

    private String getJsonPayload( ) throws JsonProcessingException {
        TokenDTO token = new TokenDTO();
        token.setId(ID);
        token.setUser(USER_ID);
        token.setDescription(DESCRIPTION);
        token.setActive(ACTIVE);
        token.setToken(TOKEN);
        token.setParent(PARENT_ID);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(token);
    }


}
