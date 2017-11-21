package co.aisystem.hibernate;

import co.aisystem.hibernate.domain.Person;
import co.aisystem.hibernate.domain.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static co.aisystem.hibernate.domain.types.Status.ENABLED;
import static java.time.LocalDateTime.now;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
@EnableAsync
//@EnableJpaRepositories(repositoryBaseClass = PersonRepository.class)
@EnableTransactionManagement
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
                    .status(ENABLED)
                    .firstVisit(true)
                    .build());
            repository.save(Person.builder()
                    .name("bronek")
                    .surname("zenowicz")
                    .date(now())
                    .status(ENABLED)
                    .firstVisit(true)
                    .build());
            repository.save(Person.builder()
                    .name("marek")
                    .surname("danielewicz")
                    .date(now())
                    .status(ENABLED)
                    .firstVisit(true)
                    .build());

            repository.findByName("andrzej")
                    .forEach(p -> log.info(p.toString()));
        };
    }
}

