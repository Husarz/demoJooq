package co.aisystem.hibernate;

import co.aisystem.hibernate.domain.Person;
import co.aisystem.hibernate.domain.PersonRepository;
import co.aisystem.hibernate.service.PersonExportService;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.Attribute;

import static co.aisystem.hibernate.domain.Person_.id;
import static co.aisystem.hibernate.domain.Person_.name;
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

//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//        // …
//    }

//    @Bean
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



//    @Bean
//    public PersonExportCsv personExportCsv(EntityManager em, PersonRepository personRepository) {
//        return new PersonExportCsv(em, personRepository);
//    }

//    @Bean
    public CommandLineRunner testExport(PersonExportService service) {
        log.info("testExport " );
        return args -> {
            service.exportMemberToCsv
                    (
                            null,
                            List.of(id, name)
                                    .map(Attribute::getName)
                                    .asJava()
                    )
                    .thenAccept(s -> log.info("csv : \n{}", s));
        };
    }
}

