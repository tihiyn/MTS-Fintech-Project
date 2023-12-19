import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class CreateAnimalServiceImpl implements CreateAnimalService{
    // переопределённый метод для создания животных при помощи цикла do-while
    @Override
    public void createAnimals() {
        // счётчик
        int counter = 1;
        // массив пород (по 3 для каждого животного)
        String[] breeds = new String[] {"Британская", "Шотландская", "Сфинкс", "Немецкая овчарка", "Доберман", "Лабрадор", "Тигровая", "Белая", "Молот", "Полярный", "Ньюфаундлендский", "Японский"};
        // массив имён (по 3 для каждого животного)
        String[] names = new String[] {"Мурзик", "Васька", "Матроскин", "Жучка", "Дружок", "Бобик", "Аква", "Щарки", "Волна", "Клык", "Волчок", "Серый"};
        // массив характеров (по 3 для каждого животного)
        String[] characters = new String[] {"Добрый", "Злой", "Игривый", "Вредный", "Верный", "Пугливый"};
        // переменная класса Animal
        Animal animal;

        do {
            // сгенерируем случайное число от 0 до 3, которое будет сопоставлено с одним из классов животных
            int numOfClass = ThreadLocalRandom.current().nextInt(4);
            // сгенерируем случайную цену
            BigDecimal randCost = new BigDecimal(ThreadLocalRandom.current().nextDouble(10000, 500000)).setScale(2, RoundingMode.HALF_UP);

            // создание животного
            animal = switch (numOfClass) {
                case 0 ->
                        new Cat(breeds[ThreadLocalRandom.current().nextInt(3)], names[ThreadLocalRandom.current().nextInt(3)], randCost, characters[ThreadLocalRandom.current().nextInt(6)]);
                case 1 ->
                        new Dog(breeds[ThreadLocalRandom.current().nextInt(3, 6)], names[ThreadLocalRandom.current().nextInt(3, 6)], randCost, characters[ThreadLocalRandom.current().nextInt(6)]);
                case 2 ->
                        new Shark(breeds[ThreadLocalRandom.current().nextInt(6, 9)], names[ThreadLocalRandom.current().nextInt(6, 9)], randCost, characters[ThreadLocalRandom.current().nextInt(6)]);
                case 3 ->
                        new Wolf(breeds[ThreadLocalRandom.current().nextInt(9, 12)], names[ThreadLocalRandom.current().nextInt(9, 12)], randCost, characters[ThreadLocalRandom.current().nextInt(6)]);
                default -> null;
            };
            // логирование
            System.out.println(counter + "-ое животное: " + animal.getClass().getName());
            System.out.println("Порода: " + animal.getBreed());
            System.out.println("Кличка: " + animal.getName());
            System.out.println("Цена: " + animal.getCost());
            System.out.println("Характер: " + animal.getCharacter());
            System.out.println("Голос: " + animal.getVoice());
            System.out.println();
        }
        while (counter++ < 10);
    }

    /* перегруженный метод для создания животных при
    // помощи цикла for с заданным аргументом N - количество животных*/
    public void createAnimals(int N) {
        for (int i = 0; i < N; i++) {
            // массив пород (по 3 для каждого животного)
            String[] breeds = new String[] {"Британская", "Шотландская", "Сфинкс", "Немецкая овчарка", "Доберман", "Лабрадор", "Тигровая", "Белая", "Молот", "Полярный", "Ньюфаундлендский", "Японский"};
            // массив имён (по 3 для каждого животного)
            String[] names = new String[] {"Мурзик", "Васька", "Матроскин", "Жучка", "Дружок", "Бобик", "Аква", "Щарки", "Волна", "Клык", "Волчок", "Серый"};
            // массив характеров (по 3 для каждого животного)
            String[] characters = new String[] {"Добрый", "Злой", "Игривый", "Вредный", "Верный", "Пугливый"};
            // переменная класса Animal
            Animal animal;

            // сгенерируем случайное число от 0 до 3, которое будет сопоставлено с одним из классов животных
            int numOfClass = ThreadLocalRandom.current().nextInt(4);
            // сгенерируем случайную цену
            BigDecimal randCost = new BigDecimal(ThreadLocalRandom.current().nextDouble(10000, 500000)).setScale(2, RoundingMode.HALF_UP);

            // создание животного
            animal = switch (numOfClass) {
                case 0 ->
                        new Cat(breeds[ThreadLocalRandom.current().nextInt(3)], names[ThreadLocalRandom.current().nextInt(3)], randCost, characters[ThreadLocalRandom.current().nextInt(6)]);
                case 1 ->
                        new Dog(breeds[ThreadLocalRandom.current().nextInt(3, 6)], names[ThreadLocalRandom.current().nextInt(3, 6)], randCost, characters[ThreadLocalRandom.current().nextInt(6)]);
                case 2 ->
                        new Shark(breeds[ThreadLocalRandom.current().nextInt(6, 9)], names[ThreadLocalRandom.current().nextInt(6, 9)], randCost, characters[ThreadLocalRandom.current().nextInt(6)]);
                case 3 ->
                        new Wolf(breeds[ThreadLocalRandom.current().nextInt(9, 12)], names[ThreadLocalRandom.current().nextInt(9, 12)], randCost, characters[ThreadLocalRandom.current().nextInt(6)]);
                default -> null;
            };
            // логирование
            System.out.println(i + 1 + "-ое животное: " + animal.getClass().getName());
            System.out.println("Порода: " + animal.getBreed());
            System.out.println("Кличка: " + animal.getName());
            System.out.println("Цена: " + animal.getCost());
            System.out.println("Характер: " + animal.getCharacter());
            System.out.println("Голос: " + animal.getVoice());
            System.out.println();
        }
    }
}
