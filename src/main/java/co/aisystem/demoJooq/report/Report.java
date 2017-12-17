package co.aisystem.demoJooq.report;

import org.jooq.JSONFormat;
import org.jooq.Record;
import org.jooq.Select;

import static org.jooq.JSONFormat.DEFAULT_FOR_RECORDS;

public interface Report extends DSLReport{

    Select<? extends Record> getQuery();

//    List<String> getFilters();

    String getName();

    @SuppressWarnings("unchecked")
    default <T extends Select> T castQuery() {
        return (T) getQuery();
    }

    default String resultToJson() {
        return getQuery()
                .fetch()
                .formatJSON(formatJson());
    }

    static JSONFormat formatJson() {
        return DEFAULT_FOR_RECORDS
                .header(true)
                .format(true);
    }
}
