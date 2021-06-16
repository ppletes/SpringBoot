package com.example.demo.heist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

//@Configuration
public class HeistConfig {

    @Bean
    CommandLineRunner commandLineRunnerHeist(HeistRepository repository) {
        return args -> {
        };
    }

}
