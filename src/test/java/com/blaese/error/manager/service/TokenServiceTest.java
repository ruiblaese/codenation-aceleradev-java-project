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

import java.util.Optional;

import static org.junit.Assert.assertTrue;

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

    }

    @Test
    public void testFindByToken() {
        Optional<Token> token = service.findByToken("email@teste.com.br");

        assertTrue(token.isPresent());
    }

}
