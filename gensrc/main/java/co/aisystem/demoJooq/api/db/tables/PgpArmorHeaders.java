/*
 * This file is generated by jOOQ.
*/
package co.aisystem.demoJooq.api.db.tables;


import co.aisystem.demoJooq.api.db.Public;
import co.aisystem.demoJooq.api.db.tables.records.PgpArmorHeadersRecord;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PgpArmorHeaders extends TableImpl<PgpArmorHeadersRecord> {

    private static final long serialVersionUID = 1727364405;

    /**
     * The reference instance of <code>public.pgp_armor_headers</code>
     */
    public static final PgpArmorHeaders PGP_ARMOR_HEADERS = new PgpArmorHeaders();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PgpArmorHeadersRecord> getRecordType() {
        return PgpArmorHeadersRecord.class;
    }

    /**
     * The column <code>public.pgp_armor_headers.key</code>.
     */
    public final TableField<PgpArmorHeadersRecord, String> KEY = createField("key", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.pgp_armor_headers.value</code>.
     */
    public final TableField<PgpArmorHeadersRecord, String> VALUE = createField("value", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.pgp_armor_headers</code> table reference
     */
    public PgpArmorHeaders() {
        this(DSL.name("pgp_armor_headers"), null);
    }

    /**
     * Create an aliased <code>public.pgp_armor_headers</code> table reference
     */
    public PgpArmorHeaders(String alias) {
        this(DSL.name(alias), PGP_ARMOR_HEADERS);
    }

    /**
     * Create an aliased <code>public.pgp_armor_headers</code> table reference
     */
    public PgpArmorHeaders(Name alias) {
        this(alias, PGP_ARMOR_HEADERS);
    }

    private PgpArmorHeaders(Name alias, Table<PgpArmorHeadersRecord> aliased) {
        this(alias, aliased, new Field[1]);
    }

    private PgpArmorHeaders(Name alias, Table<PgpArmorHeadersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PgpArmorHeaders as(String alias) {
        return new PgpArmorHeaders(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PgpArmorHeaders as(Name alias) {
        return new PgpArmorHeaders(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public PgpArmorHeaders rename(String name) {
        return new PgpArmorHeaders(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public PgpArmorHeaders rename(Name name) {
        return new PgpArmorHeaders(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public PgpArmorHeaders call(String __1) {
        return new PgpArmorHeaders(DSL.name(getName()), null, new Field[] { 
              DSL.val(__1, org.jooq.impl.SQLDataType.CLOB)
        });
    }

    /**
     * Call this table-valued function
     */
    public PgpArmorHeaders call(Field<String> __1) {
        return new PgpArmorHeaders(DSL.name(getName()), null, new Field[] { 
              __1
        });
    }
}