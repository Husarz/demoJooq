package co.aisystem.demoJooq;

import co.aisystem.demoJooq.api.db.tables.records.PersonRecord;
import co.aisystem.demoJooq.report.PageableReport;
import co.aisystem.demoJooq.report.Report;
import co.aisystem.demoJooq.report.ReportImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.lambda.Seq;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

import static co.aisystem.demoJooq.api.db.Public.PUBLIC;
import static co.aisystem.demoJooq.api.db.tables.Person.PERSON;
import static java.util.function.Function.identity;

@SpringBootApplication
@Slf4j
public class DemoJooqApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoJooqApplication.class, args);
    }

    @RequiredArgsConstructor
    static class ReportsMap {
        private final Map<String, Report> reports;

        Report getReportByName(String name){
            return reports.get(name);
        }
    }

    @Bean
    ReportsMap reportPerson(DSLContext dsl) {
        SelectWhereStep<PersonRecord> personRecords = dsl.selectFrom(PERSON);
        ReportImpl report = ReportImpl.builder()
                .dsl(dsl)
                .name(PERSON.getName())
                .query(personRecords)
                .build();

        return new ReportsMap(
                Seq.of(report)
                        .toMap(r -> report.getName(), identity())
        );

    }

    @Bean
    CommandLineRunner schema(DSLContext dsl) {
        return __ -> {
            Queries ddl = dsl.ddl(PUBLIC);
            log.info("schema: {}", dsl.informationSchema(PUBLIC).toString());
            Seq.of(ddl.queries())
                    .map(Query::getSQL)
                    .forEach(log::info);
        };
    }

    @Bean
    CommandLineRunner runSimpleQuery(ReportsMap reports) {
        return __ -> {
            SelectWhereStep<Record> personRecords = reports.getReportByName(PERSON.getName()).castQuery();
            log.debug("result of query : {}",
                    personRecords
                            .fetch()
                            .formatCSV()
            );
        };
    }

    @Bean
    CommandLineRunner runPageDecorator(ReportsMap reports) {
        Report report = reports.getReportByName(PERSON.getName());
        return __ -> log.debug(
                PageableReport.builder()
                        .report(report)
                        .page(PageRequest.of(0, 2))
                        .build()
                        .resultToJson()
        );
    }
}
