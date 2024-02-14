package ru.mts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.mts.model.Animal;
import ru.mts.model.AnimalEnum;
import ru.mts.properties.AnimalProperties;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class CreateAnimalServiceImpl implements CreateAnimalService {
    // список типов созданных животных
    private List<AnimalEnum> animalType;
    // список животных
    private Animal[] animalsArray;

    public List<AnimalEnum> getAnimalType() {
        return animalType;
    }

    public Animal[] getAnimalsArray() {
        return animalsArray;
    }

    @Autowired
    private AnimalProperties animalProperties;


    /**
     * Метод для определения типов животных. Внутри вызвается метод для создания животных
     *
     * @Since: 1.3
     * @Author: Nikita
     */
    public void defineTypeOfAnimals() {
        // вызов метода для создания животных
        createAnimals();

        // инициализация списка типов животных
        animalType = new ArrayList<>(10);
        for (Animal animal : animalsArray) {
            switch (animal.getClass().toString()) {
                case "class ru.mts.model.Cat":
                    animalType.add(AnimalEnum.CAT);
                    break;
                case "class ru.mts.model.Dog":
                    animalType.add(AnimalEnum.DOG);
                    break;
                case "class ru.mts.model.Wolf":
                    animalType.add(AnimalEnum.WOLF);
                    break;
                case "class ru.mts.model.Shark":
                    animalType.add(AnimalEnum.SHARK);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + animal.getClass().toString());
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
