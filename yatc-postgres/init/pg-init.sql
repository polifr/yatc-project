CREATE TABLE public.t_user (
    id integer NOT NULL,
    username varchar(20) not null,
    email varchar(100) not null,
    name varchar(100) not null,
    surname varchar(100) not null,
    birthdate DATE not null,
    ts_insert timestamp,
    ts_update timestamp
);

ALTER TABLE public.t_user OWNER TO pguser;
