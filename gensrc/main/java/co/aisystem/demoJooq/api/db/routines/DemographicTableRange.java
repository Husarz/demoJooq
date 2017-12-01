/*
 * This file is generated by jOOQ.
*/
package co.aisystem.demoJooq.api.db.routines;


import co.aisystem.demoJooq.api.db.Public;
import co.aisystem.demoJooq.converters.PostgresInt4RangeBinding;

import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;
import org.jooq.lambda.tuple.Range;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DemographicTableRange extends AbstractRoutine<Range<Integer>> {

    private static final long serialVersionUID = 832006681;

    /**
     * The parameter <code>public.demographic_table_range.RETURN_VALUE</code>.
     */
    public static final Parameter<Range<Integer>> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.DefaultDataType.getDefaultDataType("int4range"), false, false, new PostgresInt4RangeBinding());

    /**
     * Create a new routine call instance
     */
    public DemographicTableRange() {
        super("demographic_table_range", Public.PUBLIC, org.jooq.impl.DefaultDataType.getDefaultDataType("int4range"), new PostgresInt4RangeBinding());

        setReturnParameter(RETURN_VALUE);
    }
}
