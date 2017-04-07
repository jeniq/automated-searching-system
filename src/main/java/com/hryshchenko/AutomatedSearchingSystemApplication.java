package com.hryshchenko;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutomatedSearchingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{Application.class}, args);
    }
}
