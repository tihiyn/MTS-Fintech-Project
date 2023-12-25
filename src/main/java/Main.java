import service.CreateAnimalService;
import service.CreateAnimalServiceImpl;

public class Main {
    public static void main(String[] args) {
        // первый объект для создания животных
        // анонимный класс, чтобы "добраться" до метода по умолчанию createAnimals()
        CreateAnimalService firstCreator = new CreateAnimalService() {
            // дополнительный переопределённый метод createAnimals()
            @Override
            public void createAnimals() {
                CreateAnimalService.super.createAnimals();
            }
        };
        // вызов метода по умолчанию createAnimals()
        firstCreator.createAnimals();

        // второй объект для создания животных
        CreateAnimalService secondCreator = new CreateAnimalServiceImpl();
        // вызов переопределённого метода createAnimals()
        secondCreator.createAnimals();

        // третий объект для создания определённого кличества животных
        CreateAnimalServiceImpl thirdCreator = new CreateAnimalServiceImpl();
        // вызов перегруженного метода createAnimals(int N)
        thirdCreator.createAnimals(5);
    }
}
