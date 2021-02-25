package com.intasect.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.intasect.service.*.mapper","com.intasect.service.admin.mapper"})
public class IntasectServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntasectServiceApplication.class, args);
    }

}