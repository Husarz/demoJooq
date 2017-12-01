/*
 * This file is generated by jOOQ.
*/
package co.aisystem.demoJooq.api.db;


import co.aisystem.demoJooq.api.db.tables.EfitnessMemberMapping;
import co.aisystem.demoJooq.api.db.tables.Person;
import co.aisystem.demoJooq.api.db.tables.PgpArmorHeaders;
import co.aisystem.demoJooq.api.db.tables.records.PgpArmorHeadersRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1890433022;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.efitness_member_mapping</code>.
     */
    public final EfitnessMemberMapping EFITNESS_MEMBER_MAPPING = co.aisystem.demoJooq.api.db.tables.EfitnessMemberMapping.EFITNESS_MEMBER_MAPPING;

    /**
     * The table <code>public.person</code>.
     */
    public final Person PERSON = co.aisystem.demoJooq.api.db.tables.Person.PERSON;

    /**
     * The table <code>public.pgp_armor_headers</code>.
     */
    public final PgpArmorHeaders PGP_ARMOR_HEADERS = co.aisystem.demoJooq.api.db.tables.PgpArmorHeaders.PGP_ARMOR_HEADERS;

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

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            EfitnessMemberMapping.EFITNESS_MEMBER_MAPPING,
            Person.PERSON,
            PgpArmorHeaders.PGP_ARMOR_HEADERS);
    }
}
