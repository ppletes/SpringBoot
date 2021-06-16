package tutorial.springboot.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tutorial.springboot.repository.PupilRepository;

@Configuration
public class PupilConfiguration {

    @Bean
    CommandLineRunner commandLineRunnerHeist(PupilRepository repository) {
        return args -> {
        };
    }
}
