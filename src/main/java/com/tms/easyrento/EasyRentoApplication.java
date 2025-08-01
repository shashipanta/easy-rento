package com.tms.easyrento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.tms.easyrento.chat.repo")
public class EasyRentoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyRentoApplication.class, args);
    }

}
