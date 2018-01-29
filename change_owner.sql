CREATE FUNCTION ch_owner() RETURNS event_trigger AS $$
DECLARE r RECORD;
BEGIN
    FOR r IN SELECT * FROM pg_event_trigger_ddl_commands() LOOP
        RAISE NOTICE 'caught % event on %', r.command_tag, r.object_identity;
        EXECUTE format('ALTER TABLE %s OWNER TO crm_role', r.object_identity);
    END LOOP;
END;
$$
LANGUAGE plpgsql;

CREATE EVENT TRIGGER tr_ch_owner_on_table
  ON ddl_command_end WHEN TAG IN ('CREATE TABLE')
  EXECUTE PROCEDURE ch_owner();

