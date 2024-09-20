package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Util {
    static ResourceBundle bundle = ResourceBundle.getBundle("database"); // database.properties
    private static final SessionFactory sessionFactory; // for Hibernate session

    // для JDBC присоединения
    public static Connection getConnection() {
        // переменные хранения данных от значений обращенных ссылок
        String url = bundle.getString("mysql.url");
        String username = bundle.getString("mysql.username");
        String password = bundle.getString("mysql.password");

        // реализация подключения
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // static init block for Hibernate settings
    static {
        try {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.url",
                    bundle.getString("mysql.url"));
            configuration.setProperty("hibernate.connection.username",
                    bundle.getString("mysql.username"));
            configuration.setProperty("hibernate.connection.password",
                    bundle.getString("mysql.password"));
            configuration.setProperty("hibernate.dialect",
                    bundle.getString("hibernate.dialect"));
            configuration.setProperty("hibernate.hbm2ddl.auto",
                    bundle.getString("hibernate.hbm2ddl.auto"));
            configuration.setProperty("hibernate.connection.driver_class",
                    bundle.getString("hibernate.connection.driver_class"));
            configuration.setProperty("hibernate.show_sql",
                    bundle.getString("hibernate.show_sql"));

            // add entity class
            configuration.addAnnotatedClass(User.class);

            // building SessionFactory of Hibernate based on configuration
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("error with creating Hibernate - ", e);
        }
    }

    public static SessionFactory HibernateConnection() {
        return sessionFactory;
    }
}
