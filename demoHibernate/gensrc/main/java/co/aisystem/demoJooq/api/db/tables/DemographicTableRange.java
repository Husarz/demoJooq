/*
 * This file is generated by jOOQ.
*/
package co.aisystem.demoJooq.api.db.tables;


import co.aisystem.demoJooq.api.db.Public;
import co.aisystem.demoJooq.api.db.tables.records.DemographicTableRangeRecord;

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
public class DemographicTableRange extends TableImpl<DemographicTableRangeRecord> {

    private static final long serialVersionUID = -903718686;

    /**
     * The reference instance of <code>public.demographic_table_range</code>
     */
    public static final DemographicTableRange DEMOGRAPHIC_TABLE_RANGE = new DemographicTableRange();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DemographicTableRangeRecord> getRecordType() {
        return DemographicTableRangeRecord.class;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled.
     */
    @java.lang.Deprecated
    public final TableField<DemographicTableRangeRecord, Object> RANGE = createField("range", org.jooq.impl.DefaultDataType.getDefaultDataType("int4range"), this, "");

    /**
     * Create a <code>public.demographic_table_range</code> table reference
     */
    public DemographicTableRange() {
        this(DSL.name("demographic_table_range"), null);
    }

    /**
     * Create an aliased <code>public.demographic_table_range</code> table reference
     */
    public DemographicTableRange(String alias) {
        this(DSL.name(alias), DEMOGRAPHIC_TABLE_RANGE);
    }

    /**
     * Create an aliased <code>public.demographic_table_range</code> table reference
     */
    public DemographicTableRange(Name alias) {
        this(alias, DEMOGRAPHIC_TABLE_RANGE);
    }

    private DemographicTableRange(Name alias, Table<DemographicTableRangeRecord> aliased) {
        this(alias, aliased, new Field[0]);
    }

    private DemographicTableRange(Name alias, Table<DemographicTableRangeRecord> aliased, Field<?>[] parameters) {
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
    public DemographicTableRange as(String alias) {
        return new DemographicTableRange(DSL.name(alias), this, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DemographicTableRange as(Name alias) {
        return new DemographicTableRange(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public DemographicTableRange rename(String name) {
        return new DemographicTableRange(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public DemographicTableRange rename(Name name) {
        return new DemographicTableRange(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public DemographicTableRange call() {
        return new DemographicTableRange(DSL.name(getName()), null, new Field[] { 
        });
    }
}
