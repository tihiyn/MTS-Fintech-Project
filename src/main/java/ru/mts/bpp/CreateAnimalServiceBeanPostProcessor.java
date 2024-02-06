package ru.mts.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.mts.service.CreateAnimalServiceImpl;

@Component
public class CreateAnimalServiceBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // проверка, чтобы BPP был только для бинов CreateAnimalServiceImpl
        if (bean instanceof CreateAnimalServiceImpl) {
            // приведение типов
            CreateAnimalServiceImpl animalService = (CreateAnimalServiceImpl) bean;
            // вызов метода для создания животных
            animalService.createAnimals();
            // вызов метода для инициализации списка типов животных
            animalService.defineTypeOfAnimals();
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
