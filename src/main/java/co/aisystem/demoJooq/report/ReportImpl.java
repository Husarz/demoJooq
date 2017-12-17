package co.aisystem.demoJooq.report;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Select;

@Getter
@RequiredArgsConstructor
@Builder
public class ReportImpl implements Report {

    @NonNull
    private final Select<? extends Record> query;
    @NonNull
    private final String name;
    @NonNull
    private final DSLContext dsl;

    @Override
    public DSLContext dsl() {
        return dsl;
    }
}
