package com.example.studysystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.studysystem.mapper")
@SpringBootApplication
public class StudySystemSimpleApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudySystemSimpleApplication.class, args);
    }
}
