import application.domain.User;
import dao.DatabaseConfig;
import dao.DatabaseConnector;
import dao.SqlAddressRepository;
import dao.SqlUserRepository;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String srcDirPath = Path.of(
                System.getProperty("user.dir"),
                "src\\main\\resources",
                "config.application.properties").toString();
        System.out.println(srcDirPath);
        //        try (Connection connection = DatabaseConnector.getDbConnection(new DatabaseConfig())) {
//            SqlUserRepository userRepository = new SqlUserRepository(connection);
//            addUser(userRepository);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static boolean addUser(SqlUserRepository userRepository) {
//        return userRepository.addUser(new User("MSN", "asd@asd", "+38050200212"));
//    }
//
//    private static boolean addPaymentAddress(SqlAddressRepository addressRepository) {
//        return false;
    }
}
