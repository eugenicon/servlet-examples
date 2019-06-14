
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

create table if not exists apartments (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar (250) NOT NULL,
    numberOfPlaces int NOT NULL,
    description varchar (2000) NOT NULL,
    address varchar (500) NOT NULL,
    type varchar (50) NOT NULL,
    owner int,
    CONSTRAINT apartments_owner_fkey FOREIGN KEY (owner)
     REFERENCES users (id)
);

create table if not exists linked_files (
    id SERIAL NOT NULL PRIMARY KEY,
    owner_id int constraint linked_files_apartments_id_fk references apartments,
    file_id int constraint linked_files_files_id_fk references files
);

create table if not exists facilities (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar(250) NOT NULL
);

create table if not exists apartment_facilities (
    id SERIAL NOT NULL PRIMARY KEY,
    apartment_id int constraint apartment_facilities_apartments_id_fk references apartments,
    facility_id int constraint apartment_facilities_facilities_id_fk references facilities
);