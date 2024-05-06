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
    private static String DRIVER_CLASS_NAME;
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static boolean SHOW_SQL;
    private static String HBM2DDL_AUTO;
    private static int POOL_SIZE;
    private static String CURRENT_SESSION_CONTEXT_CLASS;

    private static SessionFactory sessionFactory;

    static {
        try {
            initProperty();
            Configuration configuration = getConfiguration();
            sessionFactory = createSessionFactory(configuration);

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }


    private DBService() {};

    private static void initProperty() {
        DRIVER_CLASS_NAME = ApplicationContextProvider.getApplicationContext().getEnvironment().getProperty("spring.datasource.driver-class-name");
        URL = ApplicationContextProvider.getApplicationContext().getEnvironment().getProperty("spring.datasource.url");
        USERNAME = ApplicationContextProvider.getApplicationContext().getEnvironment().getProperty("spring.datasource.username");
        PASSWORD = ApplicationContextProvider.getApplicationContext().getEnvironment().getProperty("spring.datasource.password");
        SHOW_SQL = Boolean.parseBoolean(ApplicationContextProvider.getApplicationContext().getEnvironment().getProperty("spring.jpa.properties.hibernate.show_sql"));
        HBM2DDL_AUTO = ApplicationContextProvider.getApplicationContext().getEnvironment().getProperty("spring.jpa.properties.hibernate.hbm2ddl.auto");
        POOL_SIZE = Integer.parseInt(ApplicationContextProvider.getApplicationContext().getEnvironment().getProperty("spring.jpa.properties.hibernate.connection.pool_size"));
        CURRENT_SESSION_CONTEXT_CLASS = ApplicationContextProvider.getApplicationContext().getEnvironment().getProperty("spring.jpa.properties.hibernate.current_session_context_class");
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

        configuration.setProperty("hibernate.connection.driver_class", DRIVER_CLASS_NAME);
        configuration.setProperty("hibernate.connection.url", URL);
        configuration.setProperty("hibernate.connection.username", USERNAME);
        configuration.setProperty("hibernate.connection.password", PASSWORD);
        configuration.setProperty("hibernate.show_sql", SHOW_SQL);
        configuration.setProperty("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
        configuration.setProperty("hibernate.connection.pool_size", POOL_SIZE);
        configuration.setProperty("hibernate.current_session_context_class", CURRENT_SESSION_CONTEXT_CLASS);

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
