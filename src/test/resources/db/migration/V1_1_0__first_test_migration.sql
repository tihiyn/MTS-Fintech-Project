CREATE SCHEMA IF NOT EXISTS animals;

CREATE TABLE IF NOT EXISTS animals.creature (
    id_creature bigserial PRIMARY KEY,
    "name" text,
    type_id int,
    age smallint
);

CREATE TABLE IF NOT EXISTS animals.animal_type (
    id_type int PRIMARY KEY,
    "type" varchar(50),
    is_wild bool
);

ALTER TABLE animals.creature RENAME COLUMN id_creature TO id;
ALTER TABLE animals.animal_type RENAME COLUMN id_type TO id;