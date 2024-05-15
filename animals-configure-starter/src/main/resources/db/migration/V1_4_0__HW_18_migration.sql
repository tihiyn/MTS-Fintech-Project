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

CREATE OR REPLACE FUNCTION filling_empty_fields()
    RETURNS TRIGGER
    AS $$
    DECLARE
        local_type text;
BEGIN
    NEW.age = extract(YEAR FROM AGE(NEW.birth_date));
    NEW.type_id = (SELECT animal_type_id FROM animals.breed WHERE id = NEW.breed_id);
    local_type = (SELECT type FROM animals.animal_type WHERE id = NEW.type_id);

    CASE
        WHEN local_type = 'cat' THEN NEW.name = (SELECT value
                                                    FROM unnest(string_to_array('${cat_names}', ', ')) AS value
                                                    ORDER BY random()
                                                    LIMIT 1);
        WHEN local_type = 'dog' THEN NEW.name = (SELECT value
                                                    FROM unnest(string_to_array('${dog_names}', ', ')) AS value
                                                    ORDER BY random()
                                                    LIMIT 1);
        WHEN local_type = 'wolf' THEN NEW.name = (SELECT value
                                                    FROM unnest(string_to_array('${wolf_names}', ', ')) AS value
                                                    ORDER BY random()
                                                    LIMIT 1);
        WHEN local_type = 'shark' THEN NEW.name = (SELECT value
                                                    FROM unnest(string_to_array('${shark_names}', ', ')) AS value
                                                    ORDER BY random()
                                                    LIMIT 1);
        ELSE NEW.name = null;
    END CASE;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER filling_empty_fields_trigger
    BEFORE INSERT ON animals.creature
    FOR EACH ROW EXECUTE FUNCTION filling_empty_fields();

INSERT INTO animals.creature (cost, character, birth_date, breed_id, secret_information)
VALUES (round((random() * (500000 - 10000) + 10000)::numeric, 2),
        (SELECT value
         FROM unnest(string_to_array('${characters}', ', ')) AS value
         ORDER BY random()
         LIMIT 1),
        date (timestamp '1970-01-01' + random() * (NOW() - timestamp '1970-01-01')),
        (SELECT id FROM animals.breed ORDER BY RANDOM() LIMIT 1),
        (SELECT encode(value::bytea, 'base64'::text)
         FROM unnest(string_to_array('${secrets}', ', ')) AS value
         ORDER BY random()
         LIMIT 1));

INSERT INTO animals.creature (cost, character, birth_date, breed_id, secret_information)
VALUES (round((random() * (500000 - 10000) + 10000)::numeric, 2),
        (SELECT value
         FROM unnest(string_to_array('${characters}', ', ')) AS value
         ORDER BY random()
         LIMIT 1),
        date (timestamp '1970-01-01' + random() * (NOW() - timestamp '1970-01-01')),
        (SELECT id FROM animals.breed ORDER BY RANDOM() LIMIT 1),
        (SELECT encode(value::bytea, 'base64'::text)
         FROM unnest(string_to_array('${secrets}', ', ')) AS value
         ORDER BY random()
         LIMIT 1));

INSERT INTO animals.creature (cost, character, birth_date, breed_id, secret_information)
VALUES (round((random() * (500000 - 10000) + 10000)::numeric, 2),
        (SELECT value
         FROM unnest(string_to_array('${characters}', ', ')) AS value
         ORDER BY random()
         LIMIT 1),
        date (timestamp '1970-01-01' + random() * (NOW() - timestamp '1970-01-01')),
        (SELECT id FROM animals.breed ORDER BY RANDOM() LIMIT 1),
        (SELECT encode(value::bytea, 'base64'::text)
         FROM unnest(string_to_array('${secrets}', ', ')) AS value
         ORDER BY random()
         LIMIT 1));

INSERT INTO animals.creature (cost, character, birth_date, breed_id, secret_information)
VALUES (round((random() * (500000 - 10000) + 10000)::numeric, 2),
        (SELECT value
         FROM unnest(string_to_array('${characters}', ', ')) AS value
         ORDER BY random()
         LIMIT 1),
        date (timestamp '1970-01-01' + random() * (NOW() - timestamp '1970-01-01')),
        (SELECT id FROM animals.breed ORDER BY RANDOM() LIMIT 1),
        (SELECT encode(value::bytea, 'base64'::text)
         FROM unnest(string_to_array('${secrets}', ', ')) AS value
         ORDER BY random()
         LIMIT 1));

INSERT INTO animals.creature (cost, character, birth_date, breed_id, secret_information)
VALUES (round((random() * (500000 - 10000) + 10000)::numeric, 2),
        (SELECT value
         FROM unnest(string_to_array('${characters}', ', ')) AS value
         ORDER BY random()
         LIMIT 1),
        date (timestamp '1970-01-01' + random() * (NOW() - timestamp '1970-01-01')),
        (SELECT id FROM animals.breed ORDER BY RANDOM() LIMIT 1),
        (SELECT encode(value::bytea, 'base64'::text)
         FROM unnest(string_to_array('${secrets}', ', ')) AS value
         ORDER BY random()
         LIMIT 1));

INSERT INTO animals.creature (cost, character, birth_date, breed_id, secret_information)
VALUES (round((random() * (500000 - 10000) + 10000)::numeric, 2),
        (SELECT value
         FROM unnest(string_to_array('${characters}', ', ')) AS value
         ORDER BY random()
         LIMIT 1),
        date (timestamp '1970-01-01' + random() * (NOW() - timestamp '1970-01-01')),
        (SELECT id FROM animals.breed ORDER BY RANDOM() LIMIT 1),
        (SELECT encode(value::bytea, 'base64'::text)
         FROM unnest(string_to_array('${secrets}', ', ')) AS value
         ORDER BY random()
         LIMIT 1));

INSERT INTO animals.creature (cost, character, birth_date, breed_id, secret_information)
VALUES (round((random() * (500000 - 10000) + 10000)::numeric, 2),
        (SELECT value
         FROM unnest(string_to_array('${characters}', ', ')) AS value
         ORDER BY random()
         LIMIT 1),
        date (timestamp '1970-01-01' + random() * (NOW() - timestamp '1970-01-01')),
        (SELECT id FROM animals.breed ORDER BY RANDOM() LIMIT 1),
        (SELECT encode(value::bytea, 'base64'::text)
         FROM unnest(string_to_array('${secrets}', ', ')) AS value
         ORDER BY random()
         LIMIT 1));

INSERT INTO animals.creature (cost, character, birth_date, breed_id, secret_information)
VALUES (round((random() * (500000 - 10000) + 10000)::numeric, 2),
        (SELECT value
         FROM unnest(string_to_array('${characters}', ', ')) AS value
         ORDER BY random()
         LIMIT 1),
        date (timestamp '1970-01-01' + random() * (NOW() - timestamp '1970-01-01')),
        (SELECT id FROM animals.breed ORDER BY RANDOM() LIMIT 1),
        (SELECT encode(value::bytea, 'base64'::text)
         FROM unnest(string_to_array('${secrets}', ', ')) AS value
         ORDER BY random()
         LIMIT 1));

INSERT INTO animals.creature (cost, character, birth_date, breed_id, secret_information)
VALUES (round((random() * (500000 - 10000) + 10000)::numeric, 2),
        (SELECT value
         FROM unnest(string_to_array('${characters}', ', ')) AS value
         ORDER BY random()
         LIMIT 1),
        date (timestamp '1970-01-01' + random() * (NOW() - timestamp '1970-01-01')),
        (SELECT id FROM animals.breed ORDER BY RANDOM() LIMIT 1),
        (SELECT encode(value::bytea, 'base64'::text)
         FROM unnest(string_to_array('${secrets}', ', ')) AS value
         ORDER BY random()
         LIMIT 1));

INSERT INTO animals.creature (cost, character, birth_date, breed_id, secret_information)
VALUES (round((random() * (500000 - 10000) + 10000)::numeric, 2),
        (SELECT value
         FROM unnest(string_to_array('${characters}', ', ')) AS value
         ORDER BY random()
         LIMIT 1),
        date (timestamp '1970-01-01' + random() * (NOW() - timestamp '1970-01-01')),
        (SELECT id FROM animals.breed ORDER BY RANDOM() LIMIT 1),
        (SELECT encode(value::bytea, 'base64'::text)
         FROM unnest(string_to_array('${secrets}', ', ')) AS value
         ORDER BY random()
         LIMIT 1));
