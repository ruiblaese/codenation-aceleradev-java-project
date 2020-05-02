package com.blaese.error.manager.service;

import com.blaese.error.manager.entity.Log;
import com.blaese.error.manager.entity.Token;
import com.blaese.error.manager.repository.LogRepository;
import com.blaese.error.manager.util.enums.Environment;
import com.blaese.error.manager.util.enums.Level;
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
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LogServiceTest {

    @MockBean
    LogRepository repository;

    @Autowired
    LogService service;

    private static final Date DATE = new Date();
    private static final String TITLE = "error test";
    private static final String DETAILS = "stacktrace";
    private static final Environment ENV = Environment.DEVELOPMENT;
    private static final Level LEVEL = Level.DEBUG;
    private static Token token = null;


    @Before
    public void setUp() {

        List<Log> list = new ArrayList<>();
        list.add(getMockLog());
    }


    @Test
    public void testSave() {

        BDDMockito.given(repository.save(Mockito.any(Log.class))).willReturn(
                getMockLog());

        Log response = service.save(new Log());

        assertNotNull(response);
        assertEquals(response.getTitle(), TITLE);
        assertEquals(response.getDetails(), DETAILS);



    }

    private Log getMockLog() {
        Token token = new Token();
        token.setId(1L);

        Log log = new Log();
        log.setDate(DATE);
        log.setTitle(TITLE);
        log.setToken(token);
        log.setLevel(LEVEL);
        log.setDetails(DETAILS);
        log.setEnvironment(ENV);

        return log;
    }

}
