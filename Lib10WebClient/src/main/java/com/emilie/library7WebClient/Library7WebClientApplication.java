package com.emilie.library7WebClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(scanBasePackages="com.emilie.library7WebClient")
@EnableFeignClients
public class Library7WebClientApplication {

    public static void main(String[] args) {
        SpringApplication.run( Library7WebClientApplication.class, args );
    }


}
