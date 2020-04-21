package com.blaese.error.manager.controller;

import com.blaese.error.manager.dto.UserDTO;
import com.blaese.error.manager.entity.User;
import com.blaese.error.manager.service.UserService;
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
public class UserControllerTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "email@teste.com.br";
    private static final String NAME = "user test";
    private static final String PASSWORD = "123456";

    private static final String URL = "/user";

    @MockBean
    UserService service;

    @Autowired
    MockMvc mvc;

    @Test
    public void testSave() throws Exception {

        BDDMockito.given(
                service.save(Mockito.any(User.class))
        ).willReturn(getMockUser());

        mvc.perform(
                MockMvcRequestBuilders
                        .post(URL)
                        .content(getJsonPayload(ID, EMAIL, NAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                //.andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.email").value(EMAIL))
                .andExpect(jsonPath("$.data.name").value(NAME))
                .andExpect(jsonPath("$.data.password").value(PASSWORD));
    }

    @Test
    public void testSaveInvalidUser() throws Exception {

        BDDMockito.given(
                service.save(Mockito.any(User.class))
        ).willReturn(getMockUser());

        mvc.perform(
                MockMvcRequestBuilders
                        .post(URL)
                        .content(getJsonPayload(ID, "wrong email", NAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Email inválido"));
    }

    @Test
    public void testSaveInvalidUserNameEmpty() throws Exception {

        BDDMockito.given(
                service.save(Mockito.any(User.class))
        ).willReturn(getMockUser());

        mvc.perform(
                MockMvcRequestBuilders
                        .post(URL)
                        .content(getJsonPayload(ID, EMAIL, "", PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").isNotEmpty());
    }

    @Test
    public void testSaveInvalidUserPasswordEmpty() throws Exception {

        BDDMockito.given(
                service.save(Mockito.any(User.class))
        ).willReturn(getMockUser());

        mvc.perform(
                MockMvcRequestBuilders
                        .post(URL)
                        .content(getJsonPayload(ID, EMAIL, NAME, ""))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").isNotEmpty());
    }

    @Test
    public void testSaveEmptyUser() throws Exception {

        BDDMockito.given(
                service.save(Mockito.any(User.class))
        ).willReturn(getMockUser());

        mvc.perform(
                MockMvcRequestBuilders
                        .post(URL)
                        .content(getJsonPayload(null, "", "", ""))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray());
    }


    public User getMockUser() {
        User user = new User();
        user.setId(ID);
        user.setEmail(EMAIL);
        user.setName(NAME);
        user.setPassword(PASSWORD);

        return user;
    }

    public String getJsonPayload(Long id, String email, String name, String password) throws JsonProcessingException {
        UserDTO dto = new UserDTO();
        dto.setId(id);
        dto.setEmail(email);
        dto.setName(name);
        dto.setPassword(password);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);

    }

}
