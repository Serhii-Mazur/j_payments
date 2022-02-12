import api.ScriptInterpreter;
import application.port.AddressRepository;
import application.port.PaymentRepository;
import application.port.TemplateRepository;
import application.port.UserRepository;
import application.service.AddressServiceImpl;
import application.service.PaymentServiceImpl;
import application.service.TemplateServiceImpl;
import application.service.UserServiceImpl;
import dal.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnector.getDbConnection(new DatabaseConfig())) {
            UserRepository userRepository = new SqlUserRepository(connection);
            AddressRepository addressRepository = new SqlAddressRepository(connection);
            TemplateRepository templateRepository = new SqlTemplateRepository(connection);
            PaymentRepository paymentRepository = new SqlPaymentRepository(connection);

            ScriptInterpreter interpreter = new ScriptInterpreter
                    (
                            new UserServiceImpl(userRepository),
                            new AddressServiceImpl(addressRepository),
                            new TemplateServiceImpl(addressRepository, templateRepository),
                            new PaymentServiceImpl(addressRepository, templateRepository, paymentRepository)
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
