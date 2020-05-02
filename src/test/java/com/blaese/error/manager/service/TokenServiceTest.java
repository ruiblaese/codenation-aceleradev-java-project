package com.blaese.error.manager.service;

import com.blaese.error.manager.entity.Token;
import com.blaese.error.manager.entity.User;
import com.blaese.error.manager.repository.TokenRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TokenServiceTest {

    @MockBean
    TokenRepository repository;

    @Autowired
    TokenService service;

    @Before
    public void setUp(){

        BDDMockito.given(
                repository.findByTokenEquals(Mockito.anyString())
        ).willReturn(Optional.of(new Token()));

        User user = new User();
        user.setId(1L);

        Token token = new Token();
        token.setDescription("test token");
        token.setUser(user);

        List<Token> tokenList = new ArrayList<Token>();
        tokenList.add(token);

        BDDMockito.given(
                repository.findByUserIdEquals(1L)
        ).willReturn(tokenList);

    }

    @Test
    public void testFindByToken() {
        Optional<Token> token = service.findByToken("email@teste.com.br");

        assertTrue(token.isPresent());
    }

    @Test
    public void testFindByUser() {
        List<Token> tokenList = service.findAllByUserId(1L);

        assertFalse(tokenList.isEmpty());
        assertEquals(tokenList.get(0).getUser().getId(), 1L);
    }


}
