package com.kagan.flightapidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FlightApiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightApiDemoApplication.class, args);
    }

}
