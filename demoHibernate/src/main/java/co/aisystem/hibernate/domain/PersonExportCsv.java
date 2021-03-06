package co.aisystem.hibernate.domain;

import co.aisystem.hibernate.domain.specyfication.PersonCriteria;
import co.aisystem.hibernate.domain.specyfication.PersonSpecification;
import co.aisystem.hibernate.domain.types.Nip;
import co.aisystem.hibernate.service.PersonExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static co.aisystem.hibernate.domain.Person_.date;
import static co.aisystem.hibernate.domain.Person_.firstVisit;
import static co.aisystem.hibernate.domain.Person_.id;
import static co.aisystem.hibernate.domain.Person_.name;
import static co.aisystem.hibernate.domain.Person_.nip;
import static co.aisystem.hibernate.domain.Person_.status;
import static co.aisystem.hibernate.domain.Person_.surname;
import static java.lang.System.lineSeparator;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Repository
//@Scope(value = SCOPE_PROTOTYPE)
@Scope(value = SCOPE_SESSION, proxyMode = TARGET_CLASS)
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonExportCsv implements PersonExportService {

    private static final int PAGE_SIZE = 50;
    private static final String DELIMITER = ";";
    private static final Set<SingularAttribute<Person, ?>> attributes = Stream.of(id, name, surname, date, status, nip, firstVisit).collect(toSet());

    private final EntityManager em;
    private final PersonRepository personRepository;
    private List<SingularAttribute<Person, ?>> streamFiltered;

    public CompletableFuture<Integer> performPageableActionOnPerson(PersonCriteria criteria, Consumer<Person> actionOnPerson) {
        Pageable page = null;
        int rows = 0;
        Specification<Person> personSpecification = PersonSpecification
                .build(criteria)
                .orElse(null);

        Page<Person> membersPage;
        do {
            page = nonNull(page) ? page.next() : PageRequest.of(0, PAGE_SIZE).first();
            membersPage = nextPageMembersByFilterSpec(personSpecification, page);
            rows += membersPage.getContent()
                    .stream()
                    .peek(actionOnPerson)
                    .mapToInt(p -> 1)
                    .sum();
            em.clear();
        } while (membersPage.hasNext());
        return completedFuture(rows);
    }

    @Override
    @Async
    public CompletableFuture<String> exportActionMemberToCsv(PersonCriteria criteria, List<String> columnFields) {
        StringBuilder sb = new StringBuilder();

        buildCsvSchemaOperationForMemberAttributes(columnFields);

        sb.append(insertHeaderCsv());
        sb.append(lineSeparator());

        return performPageableActionOnPerson(criteria, p -> exportToCsvRow(sb, p))
                .thenApply(result -> sb.toString());
    }

    private StringBuilder exportToCsvRow(StringBuilder sb, Person p) {
        return sb.append(memberToCsvRow(p))
                .append(lineSeparator());
    }

    @Override
    @Async
    public CompletableFuture<String> exportMemberToCsv(PersonCriteria criteria, List<String> columnFields) {

        buildCsvSchemaOperationForMemberAttributes(columnFields);
        StringBuilder sb = new StringBuilder();
        Pageable page = null;
        Specification<Person> personSpecification = PersonSpecification
                .build(criteria)
                .orElse(null);

        Page<Person> membersPage;
        sb.append(insertHeaderCsv());
        do {
            page = nonNull(page) ? page.next() : PageRequest.of(0, PAGE_SIZE).first();
            sb.append(lineSeparator());
            membersPage = nextPageMembersByFilterSpec(personSpecification, page);
            sb.append(
                    membersPage.getContent()
                            .stream()
                            .map(this::memberToCsvRow)
                            .collect(joining(lineSeparator()))
            );
            em.clear();
        } while (membersPage.hasNext());
        sb.append(lineSeparator());
        return completedFuture(sb.toString());
    }

    private String insertHeaderCsv() {
        return streamFiltered.stream()
                .map(Attribute::getName)
                .collect(joining(DELIMITER));
    }

    private String memberToCsvRow(Person p) {
        return streamFiltered.stream()
                .map(a -> getValue(p, a))
                .map(String::valueOf)
                .collect(joining(DELIMITER));
    }

    private Page<Person> nextPageMembersByFilterSpec(Specification<Person> searchSpecifications, Pageable page) {
        return personRepository.findAll(searchSpecifications, page);
    }

    private void buildCsvSchemaOperationForMemberAttributes(List<String> fields) {

        if (isEmpty(fields)) {
            streamFiltered = new ArrayList<>(attributes);
        } else {
            streamFiltered = fields.stream()
                    .map(this::getAttrType)
                    .collect(toList());
        }
    }

    private SingularAttribute<Person, ?> getAttrType(String field) {
        return attributes.stream()
                .filter(a -> field.equals(a.getName()))
                .findAny()
                .orElse(null);
    }

    private Object getValue(Person entity, SingularAttribute<Person, ?> field) {
        try {
            if (field == nip) {
                return getNip(entity);
            }

            Member member = field.getJavaMember();

            if (member instanceof Method) {
                return ((Method) member).invoke(entity);
            } else if (member instanceof Field) {
                return ((Field) member).get(entity);
            } else {
                throw new IllegalArgumentException("Unexpected java member type. Expecting method or field, found: " + member);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isNip(Member m) {
        return m.getName().equals(nip.getName());
    }

    private String getNip(Person entity) {
        return ofNullable(entity.getNip())
                .map(Nip::getNip)
                .orElse(null);
    }
}
