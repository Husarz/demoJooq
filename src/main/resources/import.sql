CREATE TABLE person (
  id      BIGSERIAL PRIMARY KEY,
  name    TEXT        NOT NULL,
  surname TEXT        NOT NULL,
  date    TIMESTAMPTZ NOT NULL DEFAULT now()
);
