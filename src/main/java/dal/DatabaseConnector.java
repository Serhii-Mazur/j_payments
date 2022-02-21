package dal;

import org.postgresql.ds.PGConnectionPoolDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {
    private static PGConnectionPoolDataSource connectionPool;

    public static Connection getPooledConnection(DatabaseConfig dbConfig) throws
            ClassNotFoundException, SQLException {
        if (connectionPool == null) {
            Class.forName("org." + dbConfig.getDbType() + ".Driver");
            connectionPool = new PGConnectionPoolDataSource();
            connectionPool.setUrl(dbConfig.getUrl());
            connectionPool.setUser(dbConfig.getUser());
            connectionPool.setPassword(dbConfig.getPassword());
        }
        return connectionPool.getPooledConnection().getConnection();
    }
}