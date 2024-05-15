INSERT INTO animals.animal_type VALUES (1, 'cat', false);
INSERT INTO animals.animal_type VALUES (2, 'dog', false);
INSERT INTO animals.animal_type VALUES (3, 'wolf', true);
INSERT INTO animals.animal_type VALUES (4, 'shark', true);

INSERT INTO animals.habitat VALUES (1, 'home');
INSERT INTO animals.habitat VALUES (2, 'ocean');
INSERT INTO animals.habitat VALUES (3, 'sea');
INSERT INTO animals.habitat VALUES (4, 'steppe');
INSERT INTO animals.habitat VALUES (5, 'tundra');
INSERT INTO animals.habitat VALUES (6, 'forest');

INSERT INTO animals.animals_habitats VALUES (1, 1);
INSERT INTO animals.animals_habitats VALUES (2, 1);
INSERT INTO animals.animals_habitats VALUES (3, 4);
INSERT INTO animals.animals_habitats VALUES (3, 5);
INSERT INTO animals.animals_habitats VALUES (3, 6);
INSERT INTO animals.animals_habitats VALUES (4, 2);
INSERT INTO animals.animals_habitats VALUES (4, 3);

INSERT INTO animals.provider VALUES (1, 'Blue Sea', '+79991113344');
INSERT INTO animals.provider VALUES (2, 'White Fang', '+79551836497');
INSERT INTO animals.provider VALUES (3, 'Pet shop', '+79372853378');
INSERT INTO animals.provider VALUES (4, 'Wildzzz', '+79386281622');

INSERT INTO animals.animals_providers VALUES (1, 3);
INSERT INTO animals.animals_providers VALUES (2, 3);
INSERT INTO animals.animals_providers VALUES (3, 2);
INSERT INTO animals.animals_providers VALUES (3, 4);
INSERT INTO animals.animals_providers VALUES (4, 1);
INSERT INTO animals.animals_providers VALUES (4, 4);