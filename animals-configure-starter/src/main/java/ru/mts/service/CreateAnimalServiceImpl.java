package ru.mts.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mts.AnimalsProperties;
import ru.mts.dao.AnimalTypeDAO;
import ru.mts.dao.BreedDAO;
import ru.mts.model.Animal;
import ru.mts.model.AnimalType;
import ru.mts.model.Breed;
import ru.mts.util.DBService;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Scope("prototype")
@EnableConfigurationProperties(AnimalsProperties.class)
public class CreateAnimalServiceImpl implements CreateAnimalService {
    private static final Logger logger = LoggerFactory.getLogger(CreateAnimalServiceImpl.class);
    private final AnimalTypeDAO animalTypeDAO;
    private final BreedDAO breedDAO;
    private final AnimalsProperties animalsProperties;

    public CreateAnimalServiceImpl(AnimalTypeDAO animalTypeDAO, BreedDAO breedDAO, AnimalsProperties animalsProperties) {
        this.animalTypeDAO = animalTypeDAO;
        this.breedDAO = breedDAO;
        this.animalsProperties = animalsProperties;
    }

    @PostConstruct
    @Override
    public void initDB() {
        logger.info("Hello from init db");
        Transaction transaction = DBService.getTransaction();

        try {
            AnimalType catType = new AnimalType("cat", false);
            AnimalType dogType = new AnimalType("dog", false);
            AnimalType wolfType = new AnimalType("wolf", true);
            AnimalType sharkType = new AnimalType("shark", true);

            Breed breed1 = new Breed(breeds.get(0), catType);
            Breed breed2 = new Breed(breeds.get(1), catType);
            Breed breed3 = new Breed(breeds.get(2), catType);
            catType.setBreeds(new ArrayList<>(List.of(breed1, breed2, breed3)));

            Breed breed4 = new Breed(breeds.get(3), dogType);
            Breed breed5 = new Breed(breeds.get(4), dogType);
            Breed breed6 = new Breed(breeds.get(5), dogType);
            dogType.setBreeds(new ArrayList<>(List.of(breed4, breed5, breed6)));

            Breed breed7 = new Breed(breeds.get(6), sharkType);
            Breed breed8 = new Breed(breeds.get(7), sharkType);
            Breed breed9 = new Breed(breeds.get(8), sharkType);
            sharkType.setBreeds(new ArrayList<>(List.of(breed7, breed8, breed9)));

            Breed breed10 = new Breed(breeds.get(9), wolfType);
            Breed breed11 = new Breed(breeds.get(10), wolfType);
            Breed breed12 = new Breed(breeds.get(11), wolfType);
            sharkType.setBreeds(new ArrayList<>(List.of(breed10, breed11, breed12)));

            List<AnimalType> animalTypes = List.of(catType, dogType, wolfType, sharkType);
            animalTypeDAO.saveListAnimalType(animalTypes);

            List<Breed> breeds = List.of(breed1, breed2, breed3, breed4, breed5, breed6, breed7, breed8, breed9, breed10, breed11, breed12);
            breedDAO.saveListBreed(breeds);

            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException();
        }
    }

    /**
     * Метод, который генерирует случайную дату.
     *
     * @return случайная дата.
     * @author Nikita
     * @since 1.1
     */
    private LocalDate createRandomDate() {
        int year = ThreadLocalRandom.current().nextInt(2023 - 1970 + 1) + 1970;
        int month = ThreadLocalRandom.current().nextInt(12) + 1;
        int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        int day = ThreadLocalRandom.current().nextInt(daysInMonth) + 1;

        return LocalDate.of(year, month, day);
    }

    private String getRandomSecretInformation() {
        Path path = Paths.get("animals-configure-starter", "src", "main", "resources", "secretStore", "secretInformation.txt");

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            return lines.get(ThreadLocalRandom.current().nextInt(100));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод, отвечающий непосредственно за создание животного.
     *
     * @return объект животного.
     * @author Nikita
     * @since 1.1
     */
    private Animal commonCreating(int counter) {
        Animal animal;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        BigDecimal cost = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(10000, 500000)).setScale(2, RoundingMode.HALF_UP);
        LocalDate birthDate = createRandomDate();

        Transaction transaction = DBService.getTransaction();

        try {
            List<AnimalType> animalTypes = animalTypeDAO.listAnimalTypes();
            AnimalType animalType = animalTypes.get(ThreadLocalRandom.current().nextInt(animalTypes.size()));

            List<Breed> breeds = animalType.getBreeds();
            Breed breed = breeds.get(ThreadLocalRandom.current().nextInt(breeds.size()));

            animal = switch (animalType.getType()) {
                case "cat" -> new Animal(animalsProperties.getCatNames().get(ThreadLocalRandom.current().nextInt(animalsProperties.getCatNames().size())),
                        cost,
                        characters.get(ThreadLocalRandom.current().nextInt(6)),
                        birthDate,
                        animalType,
                        (short) Period.between(birthDate, LocalDate.now()).getYears(),
                        breed,
                        getRandomSecretInformation());
                case "dog" -> new Animal(animalsProperties.getDogNames().get(ThreadLocalRandom.current().nextInt(animalsProperties.getDogNames().size())),
                        cost,
                        characters.get(ThreadLocalRandom.current().nextInt(6)),
                        birthDate,
                        animalType,
                        (short) Period.between(birthDate, LocalDate.now()).getYears(),
                        breed,
                        getRandomSecretInformation());
                case "wolf" -> new Animal(animalsProperties.getWolfNames().get(ThreadLocalRandom.current().nextInt(animalsProperties.getWolfNames().size())),
                        cost,
                        characters.get(ThreadLocalRandom.current().nextInt(6)),
                        birthDate,
                        animalType,
                        (short) Period.between(birthDate, LocalDate.now()).getYears(),
                        breed,
                        getRandomSecretInformation());
                case "shark" ->
                        new Animal(animalsProperties.getSharkNames().get(ThreadLocalRandom.current().nextInt(animalsProperties.getSharkNames().size())),
                                cost,
                                characters.get(ThreadLocalRandom.current().nextInt(6)),
                                birthDate,
                                animalType,
                                (short) Period.between(birthDate, LocalDate.now()).getYears(),
                                breed,
                                getRandomSecretInformation());
                default -> null;
            };

            animalTypeDAO.addAnimal(animalType, animal);
            breedDAO.addAnimal(breed, animal);

            transaction.commit();
        } catch (HibernateException | NoResultException | NullPointerException e) {
            DBService.transactionRollback(transaction);
            throw new RuntimeException();
        }

        logger.info("{}-ое животное:", counter);
        logger.info("Имя: {}", animal.getName());
        logger.info("Цена: {}", animal.getCost());
        logger.info("Характер: {}", animal.getCharacter());
        logger.info("День рождения животного: {}", animal.getBirthDate().format(formatter));
        logger.info("Тип: {}", animal.getAnimalType().getType());
        logger.info("Возраст: {}", animal.getAge());
        logger.info("Порода: {}", animal.getBreed().getBreed());
        logger.info("Секретная информация: {}", animal.getSecretInformation());

        return animal;
    }

    private void writeAnimalToFile(Animal animal, int counter) {
        Path path = Paths.get("animals-configure-starter", "src", "main", "resources", "animals", "logData.txt");

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(path.toString(), "rw")) {
            if (counter == 1) {
                randomAccessFile.setLength(0);
            }

            FileChannel fileChannel = randomAccessFile.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(100);
            buffer.clear();

            String animalLogInfo = counter + " " + animal.getBreed().getBreed() + " " + animal.getName() + " " + animal.getCost().toString() + " " + animal.getBirthDate().toString() + "\n";

            buffer.put(animalLogInfo.getBytes());

            buffer.flip();

            randomAccessFile.seek(randomAccessFile.length());
            while (buffer.hasRemaining()) {
                fileChannel.write(buffer);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createAnimals() {
        int counter = 1;

        do {
            Animal animal = commonCreating(counter);
            writeAnimalToFile(animal, counter);
        }
        while (counter++ < 10);

    }


    /**
     * Перегруженный метод для создания животных при помощи цикла for.
     * Аргументы: N - количество животных, которое необходимо создать
     *
     * @return HashMap из созданных животных
     * @author Nikita
     * @since 1.1
     */
    public void createAnimals(int N) {
        for (int i = 0; i < N; i++) {
            Animal animal = commonCreating(i + 1);
            writeAnimalToFile(animal, i + 1);
        }
    }
}
