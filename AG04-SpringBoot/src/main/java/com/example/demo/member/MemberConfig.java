package com.example.demo.member;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfig {

    @Bean
    CommandLineRunner commandLineRunnerMember(MemberRepository repository) {
        return args -> {
        };
    }

}
