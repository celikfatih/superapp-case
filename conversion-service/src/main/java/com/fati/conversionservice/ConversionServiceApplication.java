package com.fati.conversionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ConversionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConversionServiceApplication.class, args);
    }

}
