ALTER TABLE animals.creature ADD FOREIGN KEY (type_id) REFERENCES animals.animal_type (id);

CREATE TABLE animals.habitat (
                                 id int PRIMARY KEY,
                                 area text
);

CREATE TABLE animals.animals_habitats (
                                          id_animal_type int,
                                          id_area int,
                                          PRIMARY KEY (id_animal_type, id_area)
);

ALTER TABLE animals.animals_habitats ADD FOREIGN KEY (id_animal_type) REFERENCES animals.animal_type (id);
ALTER TABLE animals.animals_habitats ADD FOREIGN KEY (id_area) REFERENCES animals.habitat (id);

CREATE TABLE animals.provider (
                                  id int PRIMARY KEY,
                                  name text,
                                  phone char(50)
);

CREATE TABLE animals.animals_providers (
                                           id_animal_type int,
                                           id_provider int,
                                           PRIMARY KEY (id_animal_type, id_provider)
);

ALTER TABLE animals.animals_providers ADD FOREIGN KEY (id_animal_type) REFERENCES animals.animal_type (id);
ALTER TABLE animals.animals_providers ADD FOREIGN KEY (id_provider) REFERENCES animals.provider (id);
