package com.blaese.error.manager.repository;

import com.blaese.error.manager.entity.Log;
import com.blaese.error.manager.entity.Token;
import com.blaese.error.manager.entity.User;
import com.blaese.error.manager.util.enums.Environment;
import com.blaese.error.manager.util.enums.Level;
import com.blaese.error.manager.util.enums.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LogRepositoryTest {

    private Long savedTokenId = null;
    private Long savedUserId = null;
    private Long savedLogId = null;

    @Autowired
    LogRepository repository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;



    @Before
    public void setUp(){
        User user = new User();
        user.setName("SetUp User");
        user.setPassword("123456");
        user.setEmail("email@teste.com.br");
        User responseUser = userRepository.save(user);
        savedUserId = user.getId();

        Token token = new Token();
        token.setDescription("Application Test");
        token.setActive(true);
        token.setToken(Util.generateToken());
        token.setUser(responseUser);
        Token responseToken = tokenRepository.save(token);
        savedTokenId = responseToken.getId();

        Log log = new Log();
        log.setDate(new Date());
        log.setTitle("Erro 1");
        log.setDetails("Erro Stacktrace");
        log.setEnvironment(Environment.DEVELOPMENT);
        log.setLevel(Level.DEBUG);
        log.setToken(token);
        Log responseLog = repository.save(log);
        savedLogId = responseLog.getId();

    }

    @After
    public void tearDown() {
        repository.deleteAll();
        tokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testSave(){

        Optional<Token> token = tokenRepository.findById(savedTokenId);

        Log log = new Log();
        log.setDate(new Date());
        log.setTitle("Erro 1");
        log.setDetails("Erro Stacktrace");
        log.setEnvironment(Environment.DEVELOPMENT);
        log.setLevel(Level.DEBUG);
        log.setToken(token.get());
        Log response = repository.save(log);

        assertNotNull(response);
    }

    @Test
    public void testUpdate() {


    }

    @Test
    public void testDelete() {


    }


}
