package dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class DatabaseConfig {
    private final String user;
    private final String password;
    private final String url;

    public DatabaseConfig() {
        final Properties dbProperties = new Properties();
        Path conf = Path.of(
                System.getProperty("user.dir"),
                "src\\main\\resources",
                "config.dbaccess.properties");
        try {
            dbProperties.load(Files.newBufferedReader(conf));
        } catch (IOException e) {
            e.printStackTrace();    // TODO: modify this block
        }
        this.user = dbProperties.getProperty("user");
        this.password = dbProperties.getProperty("password");
        this.url =
                "jdbc:" +
                        dbProperties.getProperty("dbtype") + "://" +
                        dbProperties.getProperty("dbhost") + ":" +
                        dbProperties.getProperty("dbport") + "/" +
                        dbProperties.getProperty("dbname");
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
}