package co.aisystem.demoJooq.converters;

import org.jooq.Converter;
import org.jooq.lambda.tuple.Range;
import org.jooq.lambda.tuple.Tuple;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.regex.Pattern.compile;

public class Int4RangeConverter implements Converter<Object, Range<Integer>> {
    private static final Pattern RANGE_PATTERN = compile("\\[(.*?),(.*?)\\)");

    @Override
    public Range<Integer> from(Object databaseObject) {
        if (isNull(databaseObject)) {
            return null;
        }
        Matcher m = RANGE_PATTERN.matcher("" + databaseObject);
        if (m.find())
            return Tuple.range(
                    Integer.valueOf(m.group(1)),
                    Integer.valueOf(m.group(2)));

        throw new IllegalArgumentException("Unsupported Postgres type range : " + databaseObject);
    }

    @Override
    public Object to(Range<Integer> userObject) {
        return isNull(userObject) ? null : "[" + userObject.v1 + "," + userObject.v2 + ")";
    }

    @Override
    public Class<Object> fromType() {
        return Object.class;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Class<Range<Integer>> toType() {
        return (Class<Range<Integer>>) Range.class.getComponentType();
    }
}