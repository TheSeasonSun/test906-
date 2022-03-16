package com.itmk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//删掉exclude = {SecurityAutoConfiguration.class } ，开启Spring Security验证
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
//@SpringBootApplication()
public class PartApplication {
    public static void main(String[] args) {
        SpringApplication.run(PartApplication.class, args);
    }
}
