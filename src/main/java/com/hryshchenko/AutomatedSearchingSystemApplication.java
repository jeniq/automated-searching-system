package com.hryshchenko;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class AutomatedSearchingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomatedSearchingSystemApplication.class, args);
    }
}
