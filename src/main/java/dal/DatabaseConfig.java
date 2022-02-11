package dal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class DatabaseConfig {
    private final String dbType;
    private final String dbSchema;
    private final String user;
    private final String password;
    private final String url;

    public DatabaseConfig() throws ConfigDBException {
        final Properties dbProperties = new Properties();
        Path conf = Path.of(
                System.getProperty("user.dir"),
                "src\\main\\resources",
                "config.dbaccess.properties");
        try {
            dbProperties.load(Files.newBufferedReader(conf));
        } catch (IOException e) {
            throw new ConfigDBException("Can't load database properties. ", e);
//            e.printStackTrace();    // TODO: modify this block
        }
        this.dbType = dbProperties.getProperty("dbtype");
        this.dbSchema = dbProperties.getProperty("dbschema");
        this.user = dbProperties.getProperty("user");
        this.password = dbProperties.getProperty("password");
        this.url =
                "jdbc:" + dbType + "://" +
                        dbProperties.getProperty("dbhost") + ":" +
                        dbProperties.getProperty("dbport") + "/" +
                        dbProperties.getProperty("dbname");
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

    public class ConfigDBException extends Exception {
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