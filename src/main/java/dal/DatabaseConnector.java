package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static Connection connection;

    public static Connection getDbConnection(DatabaseConfig dbConfig)
            throws ClassNotFoundException, SQLException {

        if (connection == null) {
            Class.forName("org."+ dbConfig.getDbType() + ".Driver");
            connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
        }
        return connection;
    }
}