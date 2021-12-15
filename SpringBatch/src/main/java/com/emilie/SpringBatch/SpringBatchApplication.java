package com.emilie.SpringBatch;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients("com.emilie.SpringBatch")
public class SpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run( SpringBatchApplication.class, args );
    }
}




