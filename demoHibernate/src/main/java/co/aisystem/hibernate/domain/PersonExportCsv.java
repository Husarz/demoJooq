package co.aisystem.hibernate.domain;

import co.aisystem.hibernate.domain.specyfication.PersonCriteria;
import co.aisystem.hibernate.domain.specyfication.PersonSpecification;
import co.aisystem.hibernate.service.PersonExportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static co.aisystem.hibernate.domain.Person_.*;
import static com.fasterxml.jackson.core.JsonGenerator.Feature.IGNORE_UNKNOWN;
import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.BOOLEAN;
import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;
import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.STRING;
import static io.vavr.API.*;
import static io.vavr.collection.List.ofAll;
import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.joining;
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

    private static final int PAGE_SIZE = 10;
    private static final char DELIMITER = ';';
    private static final Set<Attribute> attributes = Stream.of(id, name, surname, date, status, nip, firstVisit).collect(toSet());
    private static final Set<String> setAttrNames = attributes.stream().map(Attribute::getName).collect(toSet());

    private final EntityManager em;
    private final PersonRepository personRepository;
    private ObjectWriter objectWriter;
    private CsvSchema.Builder schemaBuilder;

    @Override
    @Async
    public CompletableFuture<String> exportMemberToCsv(PersonCriteria criteria, List<String> columnFields) {

        objectWriter = buildCsvSchemaForMemberAttributes(columnFields);
        StringBuilder sb = new StringBuilder();
        Pageable page = null;
        Specification<Person> personSpecification = PersonSpecification
                .build(criteria)
                .orElse(null);

        Page<Person> membersPage;
        do {
            page = nonNull(page) ? page.next() : PageRequest.of(0, PAGE_SIZE).first();
            membersPage = nextPageMembersByFilterSpec(personSpecification, page);
            sb.append(
                    membersPage.getContent()
                            .stream()
                            .map(this::memberToCsvRow)
                            .collect(joining())
            );
            em.clear();
        } while (membersPage.hasNext());

        return completedFuture(sb.toString());
    }

    private String memberToCsvRow(Person p) {
        try {
            return objectWriter.writeValueAsString(p);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Page<Person> nextPageMembersByFilterSpec(Specification<Person> searchSpecifications, Pageable page) {
        return personRepository.findAll(searchSpecifications, page);
    }

    private ObjectWriter buildCsvSchemaForMemberAttributes(List<String> fields) {
        schemaBuilder = new CsvSchema.Builder();
        Stream<String> stream;
        if (isEmpty(fields)) {
            log.debug("buildCsvSchema for ALL fields: {}", attributes.stream().map(Attribute::getName)
                    .collect(joining(", ")));
            stream = attributes.stream().map(Attribute::getName);
        } else {
            log.debug("trying buildCsvSchema for fields: {}", fields.stream()
                    .collect(joining(", ")));
            stream = fields.stream().filter(setAttrNames::contains);
        }

        stream.distinct()
                .peek(a -> log.debug("for attr: {}", a))
                .forEach(this::addColumnToCsvSchema);
        schemaBuilder.setColumnSeparator(DELIMITER)
                .setNullValue(" - ");

        CsvSchema schema = schemaBuilder.build();

        log.debug("schema size: {}", schema.size());
        return new CsvMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .writerFor(Person.class)
                .with(schema)
                .with(IGNORE_UNKNOWN)
                .with(DateFormat.getDateTimeInstance());
    }

    private CsvSchema.Builder addColumnToCsvSchema(String a) {
        if("nip".equals(a)){
            return schemaBuilder.addColumn("nip.nip", STRING);
        }else {

            return schemaBuilder.addColumn(a, getColumnType(a));
        }
    }

    @NotNull
    private CsvSchema.ColumnType getColumnType(String columnField) {
        return Match(columnField).of(
                Case($(this::isNumberType), NUMBER),
                Case($(this::isBooleanType), BOOLEAN),
                Case($(), STRING)
        );
    }

    private boolean isNumberType(String column) {
        return ofAll(attributes)
                .filter(a -> a.getName().equalsIgnoreCase(column))
                .exists(a -> asList(Integer.class, Long.class).contains(a.getJavaType()));
    }

    private boolean isBooleanType(String column) {
        return ofAll(attributes)
                .filter(a -> a.getName().equalsIgnoreCase(column))
                .exists(a -> Boolean.class.equals(a.getJavaType()));
    }
}
