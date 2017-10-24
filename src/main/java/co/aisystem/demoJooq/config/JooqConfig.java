package co.aisystem.demoJooq.config;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JooqConfig {

    final DataSource dataSource;

    public JooqConfig(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    DSLContext dslContext(org.jooq.Configuration configuration) {
        return new DefaultDSLContext(configuration);
    }

    @Bean
    org.jooq.Configuration configuration() {
        return new DefaultConfiguration()
                .derive(SQLDialect.POSTGRES_9_5)
                .set(dataSource)
                .set(new SpringExceptionTranslationExecuteListener());
    }
}
