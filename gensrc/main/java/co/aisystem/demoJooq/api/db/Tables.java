/*
 * This file is generated by jOOQ.
*/
package co.aisystem.demoJooq.api.db;


import co.aisystem.demoJooq.api.db.tables.DemographicTableRange;
import co.aisystem.demoJooq.api.db.tables.EfitnessMemberMapping;
import co.aisystem.demoJooq.api.db.tables.Person;
import co.aisystem.demoJooq.api.db.tables.PgpArmorHeaders;
import co.aisystem.demoJooq.api.db.tables.records.DemographicTableRangeRecord;
import co.aisystem.demoJooq.api.db.tables.records.PgpArmorHeadersRecord;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.impl.DSL;


/**
 * Convenience access to all tables in public
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.demographic_table_range</code>.
     */
    public static final DemographicTableRange DEMOGRAPHIC_TABLE_RANGE = co.aisystem.demoJooq.api.db.tables.DemographicTableRange.DEMOGRAPHIC_TABLE_RANGE;

    /**
     * Call <code>public.demographic_table_range</code>.
     */
    public static Result<DemographicTableRangeRecord> DEMOGRAPHIC_TABLE_RANGE(Configuration configuration) {
        return DSL.using(configuration).selectFrom(co.aisystem.demoJooq.api.db.tables.DemographicTableRange.DEMOGRAPHIC_TABLE_RANGE.call()).fetch();
    }

    /**
     * Get <code>public.demographic_table_range</code> as a table.
     */
    public static DemographicTableRange DEMOGRAPHIC_TABLE_RANGE() {
        return co.aisystem.demoJooq.api.db.tables.DemographicTableRange.DEMOGRAPHIC_TABLE_RANGE.call();
    }

    /**
     * The table <code>public.efitness_member_mapping</code>.
     */
    public static final EfitnessMemberMapping EFITNESS_MEMBER_MAPPING = co.aisystem.demoJooq.api.db.tables.EfitnessMemberMapping.EFITNESS_MEMBER_MAPPING;

    /**
     * The table <code>public.person</code>.
     */
    public static final Person PERSON = co.aisystem.demoJooq.api.db.tables.Person.PERSON;

    /**
     * The table <code>public.pgp_armor_headers</code>.
     */
    public static final PgpArmorHeaders PGP_ARMOR_HEADERS = co.aisystem.demoJooq.api.db.tables.PgpArmorHeaders.PGP_ARMOR_HEADERS;

    /**
     * Call <code>public.pgp_armor_headers</code>.
     */
    public static Result<PgpArmorHeadersRecord> PGP_ARMOR_HEADERS(Configuration configuration, String __1) {
        return DSL.using(configuration).selectFrom(co.aisystem.demoJooq.api.db.tables.PgpArmorHeaders.PGP_ARMOR_HEADERS.call(__1)).fetch();
    }

    /**
     * Get <code>public.pgp_armor_headers</code> as a table.
     */
    public static PgpArmorHeaders PGP_ARMOR_HEADERS(String __1) {
        return co.aisystem.demoJooq.api.db.tables.PgpArmorHeaders.PGP_ARMOR_HEADERS.call(__1);
    }

    /**
     * Get <code>public.pgp_armor_headers</code> as a table.
     */
    public static PgpArmorHeaders PGP_ARMOR_HEADERS(Field<String> __1) {
        return co.aisystem.demoJooq.api.db.tables.PgpArmorHeaders.PGP_ARMOR_HEADERS.call(__1);
    }
}
