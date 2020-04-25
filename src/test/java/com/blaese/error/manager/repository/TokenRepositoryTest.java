package com.blaese.error.manager.repository;

import com.blaese.error.manager.entity.Token;
import com.blaese.error.manager.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TokenRepositoryTest {

    private Long savedTokenId = null;
    private Long savedUserId = null;

    @Autowired
    TokenRepository repository;

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
        token.setUser(responseUser);
        Token response = repository.save(token);
        savedTokenId = response.getId();
    }

    @After
    public void tearDown() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testSave(){

        Optional<User> user = userRepository.findById(savedUserId);

        Token token = new Token();
        token.setDescription("Application 1");
        token.setUser(user.get());

        Token response = repository.save(token);

        assertNotNull(response);
        assertFalse(response.getToken().isEmpty());
    }

    @Test
    public void testUpdate() {
        Optional<Token> token = repository.findById(savedTokenId);

        String description = "New Description";

        Token changed = token.get();
        changed.setDescription(description);

        repository.save(changed);

        Optional<Token> newWalletItem = repository.findById(savedTokenId);

        assertEquals(description, newWalletItem.get().getDescription());

    }

    @Test
    public void testDelete() {
        Optional<User> user = userRepository.findById(savedUserId);

        Token token = new Token();
        token.setDescription("Application 1");
        token.setUser(user.get());

        Token savedToken = repository.save(token);
        repository.deleteById(savedToken.getId());

        Optional<Token> response = repository.findById(savedToken.getId());

        assertFalse(response.isPresent());

    }




}
