CREATE DATABASE yatc;

CREATE ROLE yatc_app WITH
    LOGIN 
    PASSWORD 'yatc_app';

GRANT CONNECT ON DATABASE yatc TO yatc_app;

\connect yatc;

-- Necessaria per il monitoraggio effettuato dall'exporter
GRANT EXECUTE ON FUNCTION pg_ls_waldir() TO yatc_app;

GRANT USAGE ON SCHEMA public TO yatc_app;

-- GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO yatc_app;

-- GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO yatc_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
   GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO yatc_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
   GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO yatc_app;

CREATE TABLE t_user (
    id integer NOT NULL,
    username varchar(20) not null,
    email varchar(100) not null,
    name varchar(100) not null,
    surname varchar(100) not null,
    birthdate DATE not null,
    ts_insert timestamp,
    ts_update timestamp
);

CREATE TABLE t_event (
    id integer NOT NULL,
    message varchar(512) NOT NULL
)
