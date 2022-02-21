package dal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class DatabaseConfig {
    private final String configFilePath;
    private final String dbType;
    private final String dbHost;
    private final String dbSchema;
    private final String user;
    private final String password;
    private final String url;
    private static DatabaseConfig instance;

    public DatabaseConfig(String configFilePath, String dbType, String dbHost, String dbSchema, String user, String password, String url) {
        this.configFilePath = configFilePath;
        this.dbType = dbType;
        this.dbHost = dbHost;
        this.dbSchema = dbSchema;
        this.user = user;
        this.password = password;
        this.url = url;
    }

    public static DatabaseConfig init(String configFilePath) throws ConfigDBException {
        if (instance == null) {
            final Properties DB_PROPERTIES = new Properties();
            try {
                DB_PROPERTIES.load(Files.newBufferedReader(Path.of(configFilePath)));
            } catch (IOException e) {
                throw new ConfigDBException("Can't load database properties. ", e);
            }
            String dbType = DB_PROPERTIES.getProperty("dbtype");
            String dbHost = DB_PROPERTIES.getProperty("dbhost");
            String dbPort = DB_PROPERTIES.getProperty("dbport");
            String dbSchema = DB_PROPERTIES.getProperty("dbschema");
            String user = DB_PROPERTIES.getProperty("user");
            String password = DB_PROPERTIES.getProperty("password");
            String dbName = DB_PROPERTIES.getProperty("dbname");
            String url =
                    "jdbc:" + dbType + "://" +
                            dbHost + ":" +
                            dbPort + "/" +
                            dbName;
            instance = new DatabaseConfig(configFilePath, dbType, dbHost, dbSchema, user, password, url);
        }
        return instance;
    }

    public String getDbType() {
        return dbType;
    }

    public String getDbSchema() {
        return dbSchema;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getConfigFilePath() {
        return configFilePath;
    }

    public String getDbHost() {
        return dbHost;
    }

    public static class ConfigDBException extends Exception {
        public ConfigDBException() {
            super();
        }

        public ConfigDBException(String message) {
            super(message);
        }

        public ConfigDBException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}