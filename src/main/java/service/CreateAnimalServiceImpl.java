package service;

public class CreateAnimalServiceImpl implements CreateAnimalService {
    // переопределённый метод для создания животных при помощи цикла do-while
    @Override
    public void createAnimals() {
        // счётчик
        int counter = 1;

        do {
            commonCreating(counter);
        }
        while (counter++ < 10);
    }

    /* перегруженный метод для создания животных при
    // помощи цикла for с заданным аргументом N - количество животных*/
    public void createAnimals(int N) {
        for (int i = 0; i < N; i++) {
            commonCreating(i + 1);
        }
    }
}
