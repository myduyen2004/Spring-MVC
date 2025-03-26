package com.jobregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.jobregistration.model")
@EnableJpaRepositories("com.jobregistration.repository")
@ComponentScan(basePackages = {"com.jobregistration.controller", "com.jobregistration.service"})
public class ParttimejobApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParttimejobApplication.class, args);
    }

}
