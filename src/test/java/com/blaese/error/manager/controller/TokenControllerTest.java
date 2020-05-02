package com.blaese.error.manager.controller;

import com.blaese.error.manager.dto.TokenDTO;
import com.blaese.error.manager.entity.Token;
import com.blaese.error.manager.entity.User;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private static final String POST_GET_URL = "/token";
    private static final String PUT_URL = "/token/1";

    @Test
    @WithMockUser
    public void testSave() throws Exception{

        BDDMockito.given(service.save(Mockito.any(Token.class)))
                .willReturn(getMockToken());

        mvc.perform(
                MockMvcRequestBuilders
                        .post(POST_GET_URL)
                        .requestAttr("loggedUserId", USER_ID)
                        .content(getJsonPayload())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser
    public void testUpdate() throws Exception{

        Optional<Token> expectedToken = Optional.of(getMockToken());

        BDDMockito.given(service.findById(Mockito.anyLong()))
                .willReturn(expectedToken);

        BDDMockito.given(service.save(Mockito.any(Token.class)))
                .willReturn(getMockToken());

        mvc.perform(
                MockMvcRequestBuilders
                        .put(PUT_URL)
                        .requestAttr("loggedUserId", USER_ID)
                        .content(getJsonPayload())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    public void testUpdateChangeToken() throws Exception{

        Optional<Token> expectedToken = Optional.of(getMockToken());

        BDDMockito.given(service.findById(Mockito.anyLong()))
                .willReturn(expectedToken);

        TokenDTO token = new TokenDTO();
        token.setId(ID);
        token.setUser(USER_ID);
        token.setDescription(DESCRIPTION);
        token.setActive(ACTIVE);
        token.setToken("1234");        

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(token);

        mvc.perform(
                MockMvcRequestBuilders
                        .put(PUT_URL)
                        .requestAttr("loggedUserId", USER_ID)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Token/Chave não pode ser alterado"));

    }

    @Test
    @WithMockUser
    public void testFindAllToken() throws Exception{

        List<Token> listToken = new ArrayList<Token>();
        listToken.add(getMockToken());
        listToken.add(getMockToken());

        BDDMockito.given(service.findAllByUserId(USER_ID))
                .willReturn(listToken);

        mvc.perform(
                MockMvcRequestBuilders
                        .get(POST_GET_URL)
                        .requestAttr("loggedUserId", USER_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(ID));
    }

    private Token getMockToken(){
        User user = new User();
        user.setId(USER_ID);

        Token token = new Token();
        token.setId(ID);
        token.setUser(user);
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
        //token.setParent(PARENT_ID);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(token);
    }
    
    


}
