CREATE TABLE IF NOT EXISTS animals.my_user (
    id serial PRIMARY KEY,
    firstname varchar(255),
    lastname varchar(255),
    email varchar(255),
    password varchar(255),
    role varchar(255)
);