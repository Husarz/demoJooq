package co.aisystem.demoJooq.report;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Builder;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectLimitStep;
import org.springframework.data.domain.Pageable;

import static java.lang.Long.valueOf;

public class PageableReport extends ReportDecorator {
    private final Pageable page;

    @Builder
    public PageableReport(Report report, Pageable page) {
        super(report);
        this.page = page;
    }

    @Override
    public Select<? extends Record> getQuery() {
        return handlePageableOnReport();
    }

    private SelectLimitStep<Record> handlePageableOnReport() {
        SelectLimitStep<Record> reportQuery = castQuery();
        reportQuery
                .limit(page.getPageSize())
                .offset(getIntOffset(page));
        return reportQuery;
    }

    @Override
    public String resultToJson() {
        String json = super.resultToJson();
        return new Gson().toJson(addPagableJson(json));
    }

    private JsonObject addPagableJson(String json) {
        Integer count = dsl().fetchCount(super.getQuery());
        JsonElement jElem = new Gson().fromJson(json, JsonElement.class);
        JsonObject jObj = jElem.getAsJsonObject();
        jObj.addProperty("count", count);
        return jObj;
    }

    private int getIntOffset(Pageable page) {
        return valueOf(page.getOffset()).intValue();
    }
}
