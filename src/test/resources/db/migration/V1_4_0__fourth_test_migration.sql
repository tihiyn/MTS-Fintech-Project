CREATE TABLE IF NOT EXISTS animals.breed (
    id int PRIMARY KEY,
    breed character varying(255),
    animal_type_id int REFERENCES animals.animal_type (id)
);

ALTER TABLE animals.creature
ADD cost numeric(10, 2);

ALTER TABLE animals.creature
ADD character character varying(255);

ALTER TABLE animals.creature
ADD birth_date date;

ALTER TABLE animals.creature
ADD secret_information text;

ALTER TABLE animals.creature
ADD breed_id int REFERENCES animals.breed (id);

-- ALTER TABLE animals.creature
-- ALTER COLUMN id TYPE serial;

INSERT INTO animals.breed (id, breed, animal_type_id) VALUES (1, 'Британская', 1);
INSERT INTO animals.breed (id, breed, animal_type_id) VALUES (2, 'Шотландская', 1);
INSERT INTO animals.breed (id, breed, animal_type_id) VALUES (3, 'Сфинкс', 1);

INSERT INTO animals.breed (id, breed, animal_type_id) VALUES (4, 'Немецкая овчарка', 2);
INSERT INTO animals.breed (id, breed, animal_type_id) VALUES (5, 'Доберман', 2);
INSERT INTO animals.breed (id, breed, animal_type_id) VALUES (6, 'Лабрадор', 2);

INSERT INTO animals.breed (id, breed, animal_type_id) VALUES (7, 'Тигровая', 3);
INSERT INTO animals.breed (id, breed, animal_type_id) VALUES (8, 'Белая', 3);
INSERT INTO animals.breed (id, breed, animal_type_id) VALUES (9, 'Молот', 3);

INSERT INTO animals.breed (id, breed, animal_type_id) VALUES (10, 'Полярный', 4);
INSERT INTO animals.breed (id, breed, animal_type_id) VALUES (11, 'Ньюфаундлендский', 4);
INSERT INTO animals.breed (id, breed, animal_type_id) VALUES (12, 'Японский', 4);

