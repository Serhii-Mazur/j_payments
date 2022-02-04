import api.ScriptInterpreter;
import application.service.AddressServiceImpl;
import application.service.TemplateServiceImpl;
import application.service.UserServiceImpl;
import dao.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnector.getDbConnection(new DatabaseConfig())) {
            ScriptInterpreter interpreter = new ScriptInterpreter
                    (
                            new UserServiceImpl(new SqlUserRepository(connection)),
                            new AddressServiceImpl(new SqlAddressRepository(connection)),
                            new TemplateServiceImpl(new SqlTemplateRepository(connection))
                    );
            try {
                interpreter.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (DatabaseConfig.ConfigDBException e) {
            e.printStackTrace();
        }

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
