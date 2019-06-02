create table if not exists groups (
                                    id SERIAL NOT NULL,
                                    name varchar (250) NOT NULL,
                                    PRIMARY KEY (id)
);

create table if not exists users (
                                   id SERIAL NOT NULL,
                                   name varchar (250) NOT NULL,
                                   password varchar (250) NOT NULL,
                                   role varchar (250) NOT NULL,
                                   age int NOT NULL,
                                   group_id int,
                                   PRIMARY KEY (id),
                                   CONSTRAINT users_group_id_fkey FOREIGN KEY (group_id)
                                     REFERENCES groups (id)
)