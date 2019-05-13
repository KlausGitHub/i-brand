package com.zhongshang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ImportResource("classpath:beanRefContext.xml")
@RestController
@Slf4j
public class IBrandApplication {

    public static void main(String[] args) {
        SpringApplication.run(IBrandApplication.class, args);
    }

    @RequestMapping("/health")
    public String health() {
        return "OK";
    }
}
