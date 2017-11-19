package co.aisystem.demoJooq;

import co.aisystem.demoJooq.api.db.Public;
import co.aisystem.demoJooq.api.db.tables.Person;
import co.aisystem.demoJooq.api.db.tables.records.PersonRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Queries;
import org.jooq.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class DemoJooqApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoJooqApplication.class, args);
    }

    @Autowired
    DSLContext dsl;

    @Override
    public void run(String... strings) throws Exception {

        Queries ddl = dsl.ddl(Public.PUBLIC);
        log.info("schema: {}", dsl.informationSchema(Public.PUBLIC).toString());
        Arrays.stream(ddl.queries()).map(Query::getSQL).forEach(log::info);

        log.debug( dsl.selectFrom(Person.PERSON)
                .fetch()
                .formatCSV());
    }


//	class Runner implements CommandLineRunner {
//		@Override
//		public void run(String... strings) throws Exception {
//
//		}
//	}
//
//	@Bean
//	void schema(CommandLineRunner run){
//	    run.
////        (CommandLineRunner c) -> {
////            return c.run(Public.PUBLIC.PERSON.getSchema().toString());
////        };
//	}

}
