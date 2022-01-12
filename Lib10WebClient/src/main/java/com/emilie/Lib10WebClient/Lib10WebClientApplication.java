package com.emilie.Lib10WebClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(scanBasePackages="com.emilie.Lib10WebClient")
@EnableFeignClients
public class Lib10WebClientApplication {

    public static void main(String[] args) {
        SpringApplication.run( Lib10WebClientApplication.class, args );
    }


}
