package ru.hospitall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HospitallApp {
    public static void main(String[] args) {
        SpringApplication.run(HospitallApp.class, args);
    }
}
