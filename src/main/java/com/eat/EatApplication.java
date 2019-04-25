package com.eat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.**")
/**
 * @author Administrator
 */
public class EatApplication {
    public static void main(String[] args) {
        SpringApplication.run(EatApplication.class, args);
    }
}
