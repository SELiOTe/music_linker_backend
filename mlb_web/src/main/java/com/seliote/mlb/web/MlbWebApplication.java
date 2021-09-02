package com.seliote.mlb.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.seliote.mlb"})
@EntityScan(basePackages = "com.seliote.mlb.dao.entity")
@EnableJpaRepositories(basePackages = {"com.seliote.mlb.dao.repo"})
@EnableJpaAuditing
public class MlbWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MlbWebApplication.class, args);
    }
}
