CREATE TABLE person (
  id      BIGSERIAL PRIMARY KEY,
  name    TEXT        NOT NULL,
  surname TEXT        NOT NULL,
  date    TIMESTAMPTZ NOT NULL DEFAULT now()
);


ALTER TABLE person
  ADD COLUMN status TEXT DEFAULT 'DISABLED' NOT NULL;

ALTER TABLE person
    ADD COLUMN first_visit BOOLEAN DEFAULT FALSE NOT NULL;

ALTER TABLE person
  ADD COLUMN nip TEXT;
DROP FUNCTION demographic_table_range();
CREATE OR REPLACE FUNCTION demographic_table_range()
  RETURNS INT4RANGE
AS $$ SELECT r.v
      FROM (VALUES ('(0,24]' :: INT4RANGE),
        ('[25,30]' :: INT4RANGE),
        ('[31,34]' :: INT4RANGE),
        ('[35,40]' :: INT4RANGE),
        ('[41,50]' :: INT4RANGE),
        ('[51,)' :: INT4RANGE)) AS r(v)
$$ LANGUAGE SQL STABLE;
COMMENT ON FUNCTION demographic_table_range()
IS 'Zwraca tabelę przedzialów dla ustalonych grup wiekowych';


