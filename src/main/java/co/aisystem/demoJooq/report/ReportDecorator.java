package co.aisystem.demoJooq.report;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class ReportDecorator implements Report {

    @Delegate//(excludes = DSLReport.class)
    protected final Report report;
}
