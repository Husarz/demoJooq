package co.aisystem.demoJooq;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static co.aisystem.demoJooq.api.db.tables.Person.PERSON;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoJooqApplicationTests {

	@Autowired
	DSLContext  dsl;

	@Test
	public void contextLoads() {
		Result<Record1<String>> result = dsl.select(PERSON.NAME)
				.from(PERSON)
				.fetch();
		result.detach();
	}

}
