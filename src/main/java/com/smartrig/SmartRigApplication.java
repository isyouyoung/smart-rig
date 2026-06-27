package com.smartrig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class SmartRigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartRigApplication.class, args);
    }

}
