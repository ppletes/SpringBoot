package tutorial.springboot.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import tutorial.springboot.repository.ClassRepository;

@Configuration
public class ClassConfiguration {

    @Bean
    CommandLineRunner commandLineRunnerHeist(ClassRepository repository) {
        return args -> {
        };
    }
}
