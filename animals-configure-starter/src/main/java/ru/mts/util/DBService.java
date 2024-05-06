package ru.mts.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.ServiceException;
import ru.mts.model.*;


public abstract class DBService {

    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = getConfiguration();
            sessionFactory = createSessionFactory(configuration);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private DBService() {
    }

    public static Transaction getTransaction(){
        Session session = DBService.getSessionFactory().getCurrentSession();
        Transaction transaction = DBService.getSessionFactory().getCurrentSession().getTransaction();
        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }
        return transaction;
    }

    public static void transactionRollback(Transaction transaction){
        if (transaction.getStatus() == TransactionStatus.ACTIVE
                || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
            transaction.rollback();
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    private static Configuration getConfiguration() throws ServiceException {
        Configuration configuration = new Configuration();
        addAnnotatedClassToConfiguration(configuration);

        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/ANIMAL_SHOP");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "123456");
        configuration.setProperty("hibernate.show_sql", true);
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.connection.pool_size", 5);
        configuration.setProperty("hibernate.current_session_context_class", "thread");

        return configuration;
    }

    private static void addAnnotatedClassToConfiguration(Configuration configuration) {
        configuration.addAnnotatedClass(Animal.class)
                .addAnnotatedClass(AnimalType.class)
                .addAnnotatedClass(Breed.class)
                .addAnnotatedClass(Provider.class)
                .addAnnotatedClass(Habitat.class);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void close(){
        sessionFactory.close();
    }

//    public static boolean isTesting(){
//        return hibernate_hbm2ddl_auto.equalsIgnoreCase("create")
//                || hibernate_hbm2ddl_auto.equalsIgnoreCase("create-drop");
//    }
}
