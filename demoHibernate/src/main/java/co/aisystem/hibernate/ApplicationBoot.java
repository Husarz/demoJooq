package co.aisystem.hibernate;

import co.aisystem.hibernate.domain.Person;
import co.aisystem.hibernate.domain.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static java.time.LocalDateTime.now;

@SpringBootApplication
@Slf4j
public class ApplicationBoot {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationBoot.class);
    }

    @Bean
    public CommandLineRunner show(PersonRepository repository) {
        return (args) -> {
            repository.save(Person.builder()
                    .name("andrzej")
                    .surname("Wladkowicz")
                    .date(now())
                    .build());
            repository.save(Person.builder()
                    .name("bronek")
                    .surname("zenowicz")
                    .date(now())
                    .build());
            repository.save(Person.builder()
                    .name("marek")
                    .surname("danielewicz")
                    .date(now())
                    .build());

            repository.findByName("andrzej")
                    .forEach(p -> log.info(p.toString()));
        };

    }

}

