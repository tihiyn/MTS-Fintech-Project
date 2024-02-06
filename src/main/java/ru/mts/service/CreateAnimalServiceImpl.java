package ru.mts.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mts.model.*;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class CreateAnimalServiceImpl implements CreateAnimalService {
    // список типов созданных животных
    public List<AnimalEnum> animalType = new ArrayList<>();
    // список животных
    public Animal[] animalsArray;

    /**
     * Метод для определения типов животных
     *
     * @Since: 1.3
     * @Author: Nikita
     */
    public void defineTypeOfAnimals() {
        for (Animal animal : animalsArray) {
            if (animal.getClass() == Cat.class) {
                animalType.add(AnimalEnum.CAT);
            } else if (animal.getClass() == Dog.class) {
                animalType.add(AnimalEnum.DOG);
            } else if (animal.getClass() == Wolf.class) {
                animalType.add(AnimalEnum.WOLF);
            } else if (animal.getClass() == Shark.class) {
                animalType.add(AnimalEnum.SHARK);
            }
        }
    }


    /**
     * Переопределённый метод для создания животных при помощи цикла do-while
     *
     * @Since: 1.1
     * @Author: Nikita
     */
    @Override
    public Animal[] createAnimals() {
        // счётчик
        int counter = 1;
        // инициализация массива животных
        animalsArray = new Animal[10];

        do {
            animalsArray[counter - 1] = commonCreating(counter);
        }
        while (counter++ < 10);

//        for (AnimalEnum type: animalType) {
//            System.out.println(type);
//        }

        return animalsArray;
    }

    /**
     * Перегруженный метод для создания животных при помощи цикла for.
     * Аргументы: N - количество животных, которое необходимо создать
     *
     * @Since: 1.1
     * @Author: Nikita
     */
    public Animal[] createAnimals(int N) {
        // массив животных
        Animal[] animalsArray = new Animal[N];
        for (int i = 0; i < N; i++) {
            animalsArray[i] = commonCreating(i + 1);
        }

        return animalsArray;
    }
}
