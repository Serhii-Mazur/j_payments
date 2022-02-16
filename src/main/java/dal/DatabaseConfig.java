package dal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class DatabaseConfig {
    private final String CONFIG_FILE_PATH;
//    private final Properties DB_PROPERTIES = new Properties();
    private final String dbType;
    private final String dbSchema;
    private final String user;
    private final String password;
    private final String url;
    private static DatabaseConfig instance;

    public DatabaseConfig(String configFilePath, String dbType, String dbSchema, String user, String password, String url) {
        this.CONFIG_FILE_PATH = configFilePath;
        this.dbType = dbType;
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
            String dbSchema = DB_PROPERTIES.getProperty("dbschema");
            String user = DB_PROPERTIES.getProperty("user");
            String password = DB_PROPERTIES.getProperty("password");
            String url =
                    "jdbc:" + dbType + "://" +
                            DB_PROPERTIES.getProperty("dbhost") + ":" +
                            DB_PROPERTIES.getProperty("dbport") + "/" +
                            DB_PROPERTIES.getProperty("dbname");
            instance = new DatabaseConfig(configFilePath, dbType, dbSchema, user, password, url);
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