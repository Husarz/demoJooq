package co.aisystem.hibernate.domain;


import co.aisystem.hibernate.domain.specyfication.PersonCriteria;
import co.aisystem.hibernate.domain.specyfication.PersonSpecification;
import co.aisystem.hibernate.service.PersonExportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import io.vavr.collection.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.Attribute;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static co.aisystem.hibernate.domain.Person_.date;
import static co.aisystem.hibernate.domain.Person_.id;
import static co.aisystem.hibernate.domain.Person_.name;
import static co.aisystem.hibernate.domain.Person_.surname;
import static com.fasterxml.jackson.core.JsonGenerator.Feature.IGNORE_UNKNOWN;
import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;
import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.STRING;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static java.util.Arrays.asList;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.joining;

@Repository
//@Scope(value = SCOPE_PROTOTYPE)
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonExportCsv implements PersonExportService {

    //    private final JpaContext jpaContext;
//    private EntityManager em;
//    private final EntityManager em;
//    private final   EntityManagerFactory emf;
    private final PersonRepository personRepository;
    private ObjectWriter objectWriter;

    private static final Stream<Attribute> attributes = Stream.of(id, name, surname, date);

//    @PersistenceContext
//    public void setEntityManager(EntityManager entityManager) {
//        this.em = entityManager;
//    }

    @Override
    @Async
    public CompletableFuture<String> exportMemberToCsv(PersonCriteria criteria, List<String> columnFields) {

        buildCsvSchemaForMemberAttributes(columnFields);
//        EntityManager em = null;
//        em = emf.createEntityManager();
//        EntityManager em =  jpaContext.getEntityManagerByManagedType(Person.class);
        int page = 0;
        StringBuilder sb = new StringBuilder();
        Slice<Person> membersPage;
        Optional<Specification<Person>> filtersSpec = PersonSpecification.tryBuild();
        Specification<Person> personSpecification = filtersSpec.orElse(null);

        String csv = personRepository.findAll(personSpecification)
                .stream()
                .map(this::exportMemberToCsv)
                .collect(joining());
//        do {
//            membersPage = nextPageMembersByFilterSpec(personSpecification, page);
//            sb.append(
//                    membersPage.getContent()
//                            .stream()
//                            .map(this::exportMemberToCsv)
//                            .collect(joining())
//            );
//            em.clear();
//        } while (membersPage.hasNext());

//        return completedFuture(sb.toString());
        return completedFuture(csv);
    }

//    private EntityManager getEntityManager() {
//        EntityManagerFactory factory = null;
//        EntityManager entityManager = null;
//
//        try {
//            factory = Persistence.createEntityManagerFactory("exportControl");
//            entityManager = factory.createEntityManager();
//        } finally {
//            factory.close();
//        }
//
//        return entityManager;
//    }


    private String exportMemberToCsv(Person p) {
        try {
            return objectWriter.writeValueAsString(p);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Page<Person> nextPageMembersByFilterSpec(Specification<Person> searchSpecifications, int page) {
        return personRepository.findAll(searchSpecifications, PageRequest.of(page, 10));
    }

    private void buildCsvSchemaForMemberAttributes(List<String> fields) {

        if (fields != null) {
            log.debug("buildCsvSchema for fields: {}", fields.stream().collect(joining(", ")));
        } else {
            log.debug("buildCsvSchema for ALL fields: {}", attributes.map(Attribute::getName).collect(joining(", ")));
        }

        CsvSchema.Builder schema = new CsvSchema.Builder();
        schema.setColumnSeparator(';');
        Predicate<Attribute> isNull = fields == null ? a -> true : a -> false;
        Predicate<Attribute> getContains = a -> fields.contains(a.getName());
        attributes
                .filter(isNull.or(getContains))
                .map(Attribute::getName)
                .forEach(a -> schema.addColumn(a, getColumnType(a)));
        log.debug(">>>schema size > 0: {}: ", schema.getColumns().hasNext());
        schema.getColumns().forEachRemaining(s -> log.debug(">>>schema: {}", s.getName()));

        objectWriter = new CsvMapper()
                .writerFor(Person.class)
                .with(schema.build())
                .with(IGNORE_UNKNOWN);
    }

    @NotNull
    private CsvSchema.ColumnType getColumnType(String columnField) {
        return Match(columnField).of(
                Case($(this::isNumberType), NUMBER),
                Case($(), STRING)
        );
    }

    private boolean isNumberType(String column) {
        return attributes
                .filter(a -> a.getName().equalsIgnoreCase(column))
                .exists(a -> asList(Integer.class, Long.class).contains(a.getJavaType()));
    }
}
