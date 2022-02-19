package dal;

import org.postgresql.ds.PGConnectionPoolDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static Connection connection;

    private static PGConnectionPoolDataSource connectionPool;

    public static Connection getPooledConnection(DatabaseConfig dbConfig) throws
            ClassNotFoundException, SQLException {
        if (connectionPool == null) {
            Class.forName("org."+ dbConfig.getDbType() + ".Driver");
            connectionPool = new PGConnectionPoolDataSource();
            connectionPool.setUrl(dbConfig.getUrl());
//            connectionPool.setServerNames(new String[] {dbConfig.getDbType()});
//            connectionPool.setPortNumbers(new int[] {dbConfig.getPort()});
            connectionPool.setUser(dbConfig.getUser());
            connectionPool.setPassword(dbConfig.getPassword());
//            connectionPool.setDatabaseName();
//            connectionPool = (PGConnectionPoolDataSource) DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
        }
        return connectionPool.getPooledConnection().getConnection();
    }
    public static Connection getDbConnection(DatabaseConfig dbConfig)
            throws ClassNotFoundException, SQLException {

        if (connection == null) {
            Class.forName("org."+ dbConfig.getDbType() + ".Driver");
            connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
        }
        return connection;
    }
}