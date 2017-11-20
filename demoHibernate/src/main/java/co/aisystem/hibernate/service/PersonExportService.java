package co.aisystem.hibernate.service;

import co.aisystem.hibernate.domain.specyfication.PersonCriteria;

import java.io.DataOutput;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PersonExportService {
    CompletableFuture<String> exportMemberToCsv(PersonCriteria criteria, List<String> columnFields);
}
