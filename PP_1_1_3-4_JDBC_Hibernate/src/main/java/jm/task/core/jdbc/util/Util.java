package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/datauser";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "KAPriz148";
    private static Connection connection = null;

    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.out.println("CONNECTION OK");
        } catch (SQLException e) {
            System.out.println("NOT CONNECTION");
            throw new RuntimeException(e);

        }
        return connection;
    }
    public static void disconnected() {

        if(connection!=null){
            try {
                System.out.println("Disconnected OK");
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/datauser");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "KAPriz148");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.out.println("Problem creating session factory");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    public static void stop(){
        try {
            if(sessionFactory!=null){
                System.out.printf("Session close");
                sessionFactory.close();
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
