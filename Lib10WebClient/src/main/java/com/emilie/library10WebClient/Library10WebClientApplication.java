package com.emilie.library10WebClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(scanBasePackages="com.emilie.library10WebClient")
@EnableFeignClients
public class Library10WebClientApplication {

    public static void main(String[] args) {
        SpringApplication.run( Library10WebClientApplication.class, args );
    }


}
