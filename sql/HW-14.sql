CREATE DATABASE "ANIMAL_SHOP"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE SCHEMA animals;

CREATE TABLE animals.creature (
	id_creature bigint PRIMARY KEY,
	"name" text,
	type_id int,
	age smallint
);

CREATE TABLE animals.animal_type (
	id_type int PRIMARY KEY,
	"type" varchar(50),
	is_wild bool
);