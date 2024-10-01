package jm.task.core.jdbc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);
    static ResourceBundle bundle = ResourceBundle.getBundle("database"); // database.properties

    private Util() {} // hint from SonarLint

    // для JDBC присоединения
    public static Connection getConnection() throws SQLException {
        // переменные хранения данных от значений обращенных ссылок
        String url = bundle.getString("mysql.url");
        String username = bundle.getString("mysql.username");
        String password = bundle.getString("mysql.password");

        // реализация подключения
        try {
            logger.info("Connection was established");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new SQLException("Error connecting: " + e.getMessage());
        }
    }
}