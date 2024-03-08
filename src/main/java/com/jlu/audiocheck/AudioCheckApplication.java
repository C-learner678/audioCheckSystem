package com.jlu.audiocheck;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableWebMvc //用于配置swagger
@EnableSwagger2 //http://localhost:8080/swagger-ui/index.html
@MapperScan("com.jlu.audiocheck.mapper")
@Slf4j
@SpringBootApplication
public class AudioCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(AudioCheckApplication.class, args);
    }

}
