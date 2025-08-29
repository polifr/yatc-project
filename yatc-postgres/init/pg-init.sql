-- Creazione database ed utenza per SonarQube
CREATE ROLE sonar WITH LOGIN PASSWORD 'sonarpass';
CREATE DATABASE sonarqube OWNER sonar;
GRANT CONNECT, TEMP ON DATABASE sonarqube TO sonar;
\connect sonarqube;
GRANT ALL ON SCHEMA public TO sonar;
\connect pgadmin;

-- Creazione database ed utenza per OWASP Dependency Tracker
CREATE ROLE dtrack WITH LOGIN PASSWORD 'dtrackpass';
CREATE DATABASE dtrack OWNER dtrack;
GRANT CONNECT, TEMP ON DATABASE dtrack TO dtrack;
\connect dtrack;
GRANT ALL ON SCHEMA public TO dtrack;
\connect pgadmin;

-- Creazione database ed utenze per YATC

CREATE DATABASE yatc;

CREATE ROLE yatc_app WITH
    LOGIN 
    PASSWORD 'yatc_app';

GRANT CONNECT ON DATABASE yatc TO yatc_app;

\connect yatc;

-- Necessaria per il monitoraggio effettuato dall'exporter
-- TODO verificare se serve ancora oppure e' assorbita dalla grant on all functions
GRANT EXECUTE ON FUNCTION pg_ls_waldir() TO yatc_app;

GRANT USAGE ON SCHEMA public TO yatc_app;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO yatc_app;

GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO yatc_app;

GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public TO yatc_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
   GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO yatc_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
   GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO yatc_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
   GRANT EXECUTE ON FUNCTIONS TO yatc_app;

-- Creazione tabelle, viste, sequenze, ecc...

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

\connect pgadmin;
-- Fine della configurazione del db yatc
