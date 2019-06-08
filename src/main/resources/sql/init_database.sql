
create table if not exists groups (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar (250) NOT NULL
);

create table if not exists users (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar (250) NOT NULL,
    password varchar (250) NOT NULL,
    role varchar (250) NOT NULL,
    age int,
    group_id int,
    CONSTRAINT users_group_id_fkey FOREIGN KEY (group_id)
     REFERENCES groups (id)
);

create table if not exists files (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar(250),
    data bytea,
    size bigint
);