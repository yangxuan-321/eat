package com.eat.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {
    private static final Logger log = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){
        log.debug("name_debug = {}", "yangxuan");

        log.info("name_info = {}", "yangxuan");

        log.error("name_error = {}", "yangxuan");
    }
}
