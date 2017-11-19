package co.aisystem.hibernate.domain.export;


import co.aisystem.hibernate.domain.Person;
import co.aisystem.hibernate.domain.PersonRepository;
import co.aisystem.hibernate.domain.specyfication.PersonSpecification;
import co.aisystem.hibernate.service.PersonExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.lang.System.lineSeparator;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.joining;

@Service
//@Scope(value="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
@Slf4j
@RequiredArgsConstructor
public class PersonExportCsv implements PersonExportService {

    private final EntityManager em;

    private final  PersonRepository personRepository;

    public CompletableFuture<String> exportMemberToCsv(PersonSpecification personFindRequest, List<String> columnFields) {

        int page = 0;
        StringBuilder sb = new StringBuilder();
        Slice<Person> membersPage;
        Optional<Specification<Person>> filtersSpec = Optional.empty();
        Specification<Person> personSpecification = filtersSpec.orElseGet(null);
        do {
            membersPage = nextPageMembersByFilterSpec(personSpecification, page);
            sb.append(
                    membersPage.getContent()
                            .stream()
                            .map(this::exportMemberToCsv)
                            .collect(joining(lineSeparator()))
            );
            em.clear();
        } while (membersPage.hasNext());

        return completedFuture(sb.toString());
    }

    private String exportMemberToCsv(Person p) {
        return "dupa; dupa2";
    }

    private Page<Person> nextPageMembersByFilterSpec(Specification<Person> searchSpecifications, int page) {
        return personRepository.findAll(searchSpecifications, PageRequest.of(page, 100));
    }
}
