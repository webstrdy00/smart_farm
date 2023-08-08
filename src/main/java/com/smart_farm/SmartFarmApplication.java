package com.smart_farm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.smart_farm.repository")
public class SmartFarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartFarmApplication.class, args);
    }

}
