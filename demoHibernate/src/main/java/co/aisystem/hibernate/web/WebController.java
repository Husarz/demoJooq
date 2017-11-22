package co.aisystem.hibernate.web;

import co.aisystem.hibernate.domain.specyfication.PersonCriteria;
import co.aisystem.hibernate.service.PersonExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.metamodel.Attribute;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Function;

import static co.aisystem.hibernate.domain.Person_.id;
import static co.aisystem.hibernate.domain.Person_.name;
import static co.aisystem.hibernate.domain.Person_.surname;
import static java.util.function.Function.identity;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WebController {
    private final PersonExportService service;


    @GetMapping(value = "/person/export")
    @ResponseStatus(HttpStatus.OK)
    public Future<String> exportMember(
                                      @RequestParam List<String> fields
                                        //HttpServletResponse httpResponse
    ) throws IOException {
        PersonCriteria personC = new PersonCriteria();
//        List<String> fields  = io.vavr.collection.List.of(surname, name)
//                .map(Attribute::getName)
//                .asJava();
        return service.exportActionMemberToCsv(personC, fields);
//                .exceptionally(this::handleException)
//                .thenApply(f -> f)
    }

    private String handleException(Throwable t) {
        RuntimeException exception = new RuntimeException(t);
        log.error("exportMember Error: {}", exception);
        return exception.toString();
    }
}
