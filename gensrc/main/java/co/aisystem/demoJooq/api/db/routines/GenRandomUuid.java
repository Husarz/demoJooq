/*
 * This file is generated by jOOQ.
*/
package co.aisystem.demoJooq.api.db.routines;


import co.aisystem.demoJooq.api.db.Public;

import java.util.UUID;

import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GenRandomUuid extends AbstractRoutine<UUID> {

    private static final long serialVersionUID = 891259245;

    /**
     * The parameter <code>public.gen_random_uuid.RETURN_VALUE</code>.
     */
    public static final Parameter<UUID> RETURN_VALUE = createParameter("RETURN_VALUE", org.jooq.impl.SQLDataType.UUID, false, false);

    /**
     * Create a new routine call instance
     */
    public GenRandomUuid() {
        super("gen_random_uuid", Public.PUBLIC, org.jooq.impl.SQLDataType.UUID);

        setReturnParameter(RETURN_VALUE);
    }
}
