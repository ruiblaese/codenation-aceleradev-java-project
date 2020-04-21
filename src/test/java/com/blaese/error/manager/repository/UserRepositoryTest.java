package com.blaese.error.manager.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    private static final String EMAIL = "email@teste.com.br";

    @Autowired
    UserRepository repository;

    @Before
    public void setUp(){
        User user = new User();
        user.setName("SetUp User");
        user.setPassword("123456");
        user.setEmail(EMAIL);

        repository.save(user);
    }

    @After
    public void tearDown(){
        repository.deleteAll();
    }

    @Test
    public void testSave(){

        User user2 = new User();
        user2.setName("Test name");
        user2.setPassword("123456");
        user2.setEmail("teste@teste.com.br");

        User response = repository.save(user2);

        assertNotNull(response);
    }

    @Test
    public void testFindByEmail(){
        Optional<User> response = repository.findByEmailEquals(EMAIL);

        assertTrue(response.isPresent());
        assertEquals(response.get().getEmail(), EMAIL);

    }

}
