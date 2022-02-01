package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection getDbConnection(DatabaseConfig dbConfig)
            throws ClassNotFoundException, SQLException {
        Class.forName("org."+ dbConfig.getDbType() + ".Driver");
        return DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
    }
}