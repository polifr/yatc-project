CREATE SCHEMA yatc_schema;

GRANT USAGE ON SCHEMA yatc_schema TO yatc_app;

CREATE TABLE yatc_schema.t_user (
    id integer NOT NULL,
    username varchar(20) not null,
    email varchar(100) not null,
    name varchar(100) not null,
    surname varchar(100) not null,
    birthdate DATE not null,
    ts_insert timestamp,
    ts_update timestamp
);

ALTER TABLE yatc_schema.t_user OWNER TO yatc_app;

CREATE TABLE yatc_schema.t_event (
    id integer NOT NULL,
    message varchar(512) NOT NULL
)

ALTER TABLE yatc_schema.t_event OWNER TO yatc_app;
